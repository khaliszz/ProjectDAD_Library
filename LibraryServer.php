<?php
header('Content-Type: application/json');

class LibraryService {
    private $dbPDO;

    public function __construct() {
        $hostAddr = "localhost";
        $dbName = "projectdad";
        $dbUser = "root";
        $dbPwd = "";

        try {
            $this->dbPDO = new PDO("mysql:host=$hostAddr;dbname=$dbName", $dbUser, $dbPwd);
            $this->dbPDO->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            error_log("Database connected successfully");
        } catch (PDOException $e) {
            error_log("Connection failed: " . $e->getMessage());
            echo json_encode(array("error" => "Connection failed: " . $e->getMessage()));
            exit;
        }
    }

    public function retrieveBooks() {
        try {
            $stmt = $this->dbPDO->prepare("SELECT title, isbn, publishedYear, category FROM books WHERE copies > 0");
            $stmt->execute();
            $books = $stmt->fetchAll(PDO::FETCH_ASSOC);

            if ($books) {
                return $books;
            } else {
                return array("message" => "No books found");
            }
        } catch (PDOException $e) {
            return array("error" => "Database error: " . $e->getMessage());
        }
    }

    public function borrowBook($title, $isbn, $dateBorrowed, $status, $memberID) {
        try {
            // Check the number of books borrowed by the member
            $borrowedBooks = $this->retrieveBorrowedBooksByMember($memberID);
    
            if (is_array($borrowedBooks) && isset($borrowedBooks['error'])) {
                return array("error" => $borrowedBooks['error']);
            }
    
            // Only check the count if $borrowedBooks is not empty
            if (!empty($borrowedBooks) && count($borrowedBooks) >= 3) {
                return array("many" => "You cannot borrow more than 3 books at a time.");
            }
    
            // Begin a transaction
            $this->dbPDO->beginTransaction();
    
            // Insert into borrowedbook table
            $stmt = $this->dbPDO->prepare("INSERT INTO BorrowedBook (title, isbn, dateBorrowed, status, memberID) VALUES (:title, :isbn, :dateBorrowed, :status, :memberID)");
            $stmt->bindParam(':title', $title);
            $stmt->bindParam(':isbn', $isbn);
            $stmt->bindParam(':dateBorrowed', $dateBorrowed);
            $stmt->bindParam(':status', $status);
            $stmt->bindParam(':memberID', $memberID);
    
            if (!$stmt->execute()) {
                // Rollback transaction if insert failed
                $this->dbPDO->rollBack();
                return array("error" => "Failed to borrow book in borrowedbook table");
            }
    
            // Update books table
            $stmt = $this->dbPDO->prepare("UPDATE books SET copies = copies - 1 WHERE title = :title AND isbn = :isbn");
            $stmt->bindParam(':title', $title);
            $stmt->bindParam(':isbn', $isbn);
    
            if (!$stmt->execute()) {
                // Rollback transaction if update failed
                $this->dbPDO->rollBack();
                return array("error" => "Failed to update book copies in books table");
            }
    
            // Commit the transaction
            $this->dbPDO->commit();
    
            return array("status" => "success", "message" => "Book borrowed successfully");
        } catch (PDOException $e) {
            // Database error
            $this->dbPDO->rollBack(); // Rollback transaction on error
            return array("error" => "Database error: " . $e->getMessage());
        }
    }
    
    public function retrieveBorrowedBooks() {
        try {
            $stmt = $this->dbPDO->prepare("SELECT * FROM borrowedbook WHERE status = 'ONGOING'");
            $stmt->execute();
            $borrowedBooks = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
            if ($borrowedBooks) {
                return $borrowedBooks;
            } else {
                return array("message" => "No books found");
            }
        } catch (PDOException $e) {
            return array("error" => "Database error: " . $e->getMessage());
        }
    }

    public function retrieveBorrowedBooksByMember($memberID) {
        try {
            $stmt = $this->dbPDO->prepare("SELECT id, title, isbn, dateBorrowed, status FROM borrowedbook WHERE memberID = :memberID AND status = 'ONGOING'");
            $stmt->bindParam(':memberID', $memberID);
            $stmt->execute();
            $borrowedBooks = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
            if ($borrowedBooks) {
                // Return borrowed books data
                echo json_encode($borrowedBooks);
                return $borrowedBooks;
            } else {
                // No books found for this member
                echo json_encode(array("message" => "No books found for this member"));
            }
        } catch (PDOException $e) {
            // Database error
            echo json_encode(array("error" => "Database error: " . $e->getMessage()));
        }
    }
    

    public function returnBorrowedBook($id, $title, $isbn, $memberID) {
        try {
            // Begin a transaction
            $this->dbPDO->beginTransaction();
    
            // Update borrowedbook table
            $stmt = $this->dbPDO->prepare("UPDATE borrowedbook SET status = 'RETURNED' WHERE id = :id AND title = :title AND isbn = :isbn AND memberID = :memberID AND status = 'ONGOING'");
            $stmt->bindParam(':id', $id);
            $stmt->bindParam(':title', $title);
            $stmt->bindParam(':isbn', $isbn);
            $stmt->bindParam(':memberID', $memberID);
            
            if (!$stmt->execute()) {
                // Rollback transaction if update failed
                $this->dbPDO->rollBack();
                return array("error" => "Failed to return book in borrowedbook table");
            }
    
            // Update books table
            $stmt = $this->dbPDO->prepare("UPDATE books SET copies = copies + 1 WHERE title = :title AND isbn = :isbn");
            $stmt->bindParam(':title', $title);
            $stmt->bindParam(':isbn', $isbn);
    
            if (!$stmt->execute()) {
                // Rollback transaction if update failed
                $this->dbPDO->rollBack();
                return array("error" => "Failed to update book copies in books table");
            }
    
            // Commit the transaction
            $this->dbPDO->commit();
    
            return array("status" => "success", "message" => "Book returned successfully");
    
        } catch (PDOException $e) {
            // Rollback transaction if there is an exception
            $this->dbPDO->rollBack();
            return array("error" => "Database error: " . $e->getMessage());
        }
    }

    public function searchBookByTitle($title) {
        try {
            $stmt = $this->dbPDO->prepare("SELECT title, isbn, publishedYear, category FROM books WHERE title LIKE :title");
            $likeTitle = "%$title%";
            $stmt->bindParam(':title', $likeTitle);
            $stmt->execute();
            $Books = $stmt->fetchAll(PDO::FETCH_ASSOC);

            if ($Books) {
                return array("results" => $Books);
            } else {
                return array("message" => "No books found with the given title");
            }
        } catch (PDOException $e) {
            return array("error" => "Database error: " . $e->getMessage());
        }
    }
    
    public function searchBorrowedBookByTitle($title) {
        try {
            $stmt = $this->dbPDO->prepare("SELECT title, isbn, dateBorrowed, memberID FROM borrowedbook WHERE title LIKE :title AND status = 'ONGOING'");
            $likeTitle = "%$title%";
            $stmt->bindParam(':title', $likeTitle);
            $stmt->execute();
            $borrowedBooks = $stmt->fetchAll(PDO::FETCH_ASSOC);

            if ($borrowedBooks) {
                return array("results" => $borrowedBooks);
            } else {
                return array("message" => "No books found with the given title");
            }
        } catch (PDOException $e) {
            return array("error" => "Database error: " . $e->getMessage());
        }
    }

    public function searchBorrowedBookByID($memberID) {
        try {
            $stmt = $this->dbPDO->prepare("SELECT * FROM borrowedbook WHERE memberID = :memberID AND status = 'ONGOING'");
            $stmt->bindParam(':memberID', $memberID);
            $stmt->execute();
            $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
            if ($result) {
                return array("results" => $result);
                //return $result;
            } else {
                return array("message" => "No borrowed book for this member ID");
            }
            //return $result;
        } catch (PDOException $e) {
            return array("error" => "Database error: " . $e->getMessage());
        }
    }

    public function login($username, $password) {
        try {
            $stmt = $this->dbPDO->prepare("SELECT memberId, username, password FROM member WHERE username = :username AND password = :password AND memberStatus = 'APPROVED'");
            $stmt->bindParam(':username', $username);
            $stmt->bindParam(':password', $password);
            $stmt->execute();
            $valid = $stmt->fetch(PDO::FETCH_ASSOC);
    
            if ($valid) {
                return array("status" => "success", "message" => "Login successful", "memberId" => $valid['memberId']);
            } else {
                return array("status" => "fail", "message" => "Error Credentials");
            }
        } catch (PDOException $e) {
            return array("error" => "Database error: " . $e->getMessage());
        }
    }

    public function registerMember($username, $password, $memberStatus) {
        try {
            // Check if the username already exists
            $stmt = $this->dbPDO->prepare("SELECT COUNT(*) FROM member WHERE username = :username");
            $stmt->bindParam(':username', $username);
            $stmt->execute();
            $count = $stmt->fetchColumn();
    
            if ($count > 0) {
                // Username already exists
                return array("status" => "error", "message" => "Username already exists");
            } else {
                // Proceed with the registration
                $stmt = $this->dbPDO->prepare("INSERT INTO member (username, password, memberStatus) VALUES (:username, :password, :memberStatus)");
                $stmt->bindParam(':username', $username);
                $stmt->bindParam(':password', $password);
                $stmt->bindParam(':memberStatus', $memberStatus);
            }
    
            if ($stmt->execute()) {
                return array("status" => "success", "message" => "Member registered successfully");
            } else {
                return array("status" => "error", "message" => "Failed to register member");
            }
        } catch (PDOException $e) {
            return array("status" => "error", "message" => "Database error: " . $e->getMessage());
        }
    }
    

    public function getReturnedBooksHistory($memberID) {
        try {
            $stmt = $this->dbPDO->prepare("SELECT * FROM borrowedbook WHERE memberID = :memberID AND status = 'RETURNED'");
            $stmt->bindParam(':memberID', $memberID);
            $stmt->execute();
            $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
            if ($result) {
                return array("results" => $result);
                //return $result;
            } else {
                return array("message" => "No returned book for this member ID");
            }
            //return $result;
        } catch (PDOException $e) {
            return array("error" => "Database error: " . $e->getMessage());
        }
    }

    public function getAllReturnedBooksHistory() {
        try {
            $stmt = $this->dbPDO->prepare("SELECT * FROM borrowedbook WHERE status = 'RETURNED'");
            $stmt->execute();
            $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

            return $result;
        } catch (PDOException $e) {
            return array("error" => "Database error: " . $e->getMessage());
        }
    }

    public function approvedMember($memberID, $username, $password) {
        try {
            // Update the member's status to approved in the database
            $stmt = $this->dbPDO->prepare("UPDATE member SET memberStatus = 'APPROVED', username = :username, password = :password WHERE memberID = :memberID");
            $stmt->bindParam(':memberID', $memberID);
            $stmt->bindParam(':username', $username);
            $stmt->bindParam(':password', $password);
    
            if ($stmt->execute()) {
                return array("status" => "success", "message" => "Member approved successfully");
            } else {
                return array("error" => "Failed to approve member");
            }
        } catch (PDOException $e) {
            return array("error" => "Database error: " . $e->getMessage());
        }
    }

    public function rejectMember($memberID, $username, $password) {
        try {
            // Update the member's status to rejected in the database
            $stmt = $this->dbPDO->prepare("DELETE FROM member WHERE memberID = :memberID");
            $stmt->bindParam(':memberID', $memberID);
    
            if ($stmt->execute()) {
                return array("status" => "success", "message" => "Member rejected successfully");
            } else {
                return array("error" => "Failed to reject member");
            }
        } catch (PDOException $e) {
            return array("error" => "Database error: " . $e->getMessage());
        }
    }
    

    public function retrievePendingMember() {
        try {
            $stmt = $this->dbPDO->prepare("SELECT * FROM member WHERE memberStatus = 'PENDING'");
            $stmt->execute();
            $pendingMember = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
            if ($pendingMember) {
                return array("results" => $pendingMember);
            } else {
                return array("message" => "No pending member");
            }
        } catch (PDOException $e) {
            return array("error" => "Database error: " . $e->getMessage());
        }
    }
    
}

try {
    $libraryService = new LibraryService();
    if (isset($_GET['action'])) {
        $action = $_GET['action'];
        if ($action == 'retrieveBorrowedBooks') {
            $response = $libraryService->retrieveBorrowedBooks();
        } elseif ($action == 'retrieve') {
            $response = $libraryService->retrieveBooks();
        } elseif ($action == 'borrow' && $_SERVER['REQUEST_METHOD'] == 'POST') {
            $response = $libraryService->borrowBook($_POST['title'], $_POST['isbn'], $_POST['dateBorrowed'], $_POST['status'], $_POST['memberID']);
        } elseif ($action == 'retrieveBorrowedBooksByMember' && isset($_GET['memberID'])) {
            $memberID = $_GET['memberID'];
            $response = $libraryService->retrieveBorrowedBooksByMember($memberID);
        } elseif ($action == 'returnBorrowedBook' && $_SERVER['REQUEST_METHOD'] == 'POST') {
            $response = $libraryService->returnBorrowedBook($_POST['id'], $_POST['title'], $_POST['isbn'], $_POST['memberID']);
        } elseif ($action == 'login' && isset($_GET['username']) && isset($_GET['password'])) {
            $response = $libraryService->login($_GET['username'], $_GET['password']);
        } elseif ($action == 'register' && $_SERVER['REQUEST_METHOD'] == 'POST') {
            $response = $libraryService->registerMember($_POST['username'], $_POST['password'], $_POST['memberStatus']);
        } elseif ($action == 'searchBorrowedBook' && isset($_GET['title'])) {
            $response = $libraryService->searchBorrowedBookByTitle($_GET['title']);
        } elseif ($action == 'searchBookByTitle' && isset($_GET['title'])) {
            $response = $libraryService->searchBookByTitle($_GET['title']);
        } elseif ($action == 'getReturnedBooksHistory' && isset($_GET['memberID'])) {
            $memberID = $_GET['memberID'];
            $response = $libraryService->getReturnedBooksHistory($_GET['memberID']);
        } elseif ($action == 'getAllReturnedBooksHistory') {
            $response = $libraryService->getAllReturnedBooksHistory();
        } elseif ($action == 'searchBorrowedBookByID' && isset($_GET['memberID'])) {
            $response = $libraryService->searchBorrowedBookByID($_GET['memberID']);
        } elseif ($action == 'retrievePendingMember') {
            $response = $libraryService->retrievePendingMember();
        } elseif ($action == 'approvedMember' && $_SERVER['REQUEST_METHOD'] == 'POST') {
            $response = $libraryService->approvedMember($_POST['memberID'], $_POST['username'], $_POST['password']);
        } else if ($action == 'rejectMember' && $_SERVER['REQUEST_METHOD'] == 'POST') {
            $response = $libraryService->rejectMember($_POST['memberID'], $_POST['username'], $_POST['password']);
        }else {
            $response = array("error" => "Missing or invalid parameters");
        }
    } else {
        $response = array("error" => "No action specified");
    }

    echo json_encode($response);
} catch (Exception $e) {
    error_log($e->getMessage());
    echo json_encode(array("error" => "Exception: " . $e->getMessage()));
}
?>
