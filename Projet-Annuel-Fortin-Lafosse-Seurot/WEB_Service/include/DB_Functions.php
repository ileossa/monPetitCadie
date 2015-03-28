<?php
 
class DB_Functions {
 
    private $db;
 
    //put your code here
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }
 
    /**
     * Storing new user
     * returns user details
     */
    public function storeUser($name, $email, $password) {
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
        $result = mysqli_query($this->db->connect(), "INSERT INTO utilisateur (name, email, encrypted_password, salt) VALUES('$name', '$email', '$encrypted_password', '$salt')");
        // check for successful store
        if ($result) {
            $result = mysqli_query($this->db->connect(), "SELECT * FROM utilisateur WHERE name = '$name'");
            // return user details
            return mysqli_fetch_array($result);
        } else {
            return false;
        }
    }
    
     /**
     * Update to reset user's password
     * returns true of false
     */
    public function resetUser($name, $password) {
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
        $result = mysqli_query($this->db->connect(), "UPDATE utilisateur SET encrypted_password = '$encrypted_password', salt = '$salt' WHERE name = '$name'");
        // check for successful store
        if ($result) {           // return user details
            return true;
        } else {
            return false;
        }
    }
 
    /**
     * Get user by email and password
     */
    public function getUserByNameAndPassword($name, $password) {
        $result = mysqli_query($this->db->connect(), "SELECT * FROM utilisateur WHERE name = '$name'") or die(mysql_error());
        // check for result
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            $result = mysqli_fetch_array($result);
            $salt = $result['salt'];
            $encrypted_password = $result['encrypted_password'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $result;
            }
        } else {
            // user not found
            return false;
        }
    }
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($name) {
        $result = mysqli_query($this->db->connect(), "SELECT email from utilisateur WHERE name = '$name'");
        $no_of_rows = mysqli_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed
            return true;
        } else {
            // user not existed
            return false;
        }
    }
 
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
 
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }
    
    public function utf8ize($d) {
        if (is_array($d)) {
            foreach ($d as $k => $v) {
                $d[$k] = $this->utf8ize($v);
            }
        } else {
            return utf8_encode($d);
        }
        return $d;
    }
    
    public function getProduct() {
    $result =  mysqli_query($this->db->connect(), "SELECT * FROM produit") or die(mysql_error());
    $reponse = array();
    // check for result
    $no_of_rows = mysqli_num_rows($result);
    if ($no_of_rows > 0) {
        while ($row = mysqli_fetch_array($result)) {
            // temp user array
            $product = array();
            $product["id_produit"] = $row["id_produit"];
            $product["nom_produit"] = $row["nom_produit"];
            $product["parent"] = $row["parent"];
            $product["favori"] = $row["favori"];

            // push single product into final response array
            array_push($reponse, $product);
        }
        return $reponse;

    } else {
        // user not found
        return false;
    }
    }
    
    public function getPromo() {
    $result =  mysqli_query($this->db->connect(), "SELECT * FROM promotion") or die(mysql_error());
    $reponse = array();
    // check for result
    $no_of_rows = mysqli_num_rows($result);
    if ($no_of_rows > 0) {
        while ($row = mysqli_fetch_array($result)) {
            // temp user array
            $promo = array();
            $promo["id_promo"] = $row["id_promo"];
            $promo["nom_mag"] = $row["nom_mag"];
            $promo["desc_promo"] = $row["desc_promo"];
            $promo["date_debut"] = $row["date_debut"];
            $promo["date_fin"] = $row["date_fin"];

            // push single product into final response array
            array_push($reponse, $promo);
        }
        return $reponse;

    } else {
        // user not found
        return false;
    }
    }
    
     /**
     * Storing new liste
     * return liste uid
     */
    public function storeListe($nom_liste, $nom_utilisateur, $modele) {
        $db = new DB_Connect();
        $con = $db->connect();
        $result = mysqli_query($con, "INSERT INTO liste (nom_liste, nom_utilisateur, modele) VALUES('$nom_liste', '$nom_utilisateur', '$modele')");

        if ($result) {
            $uid = mysqli_insert_id($con);
            return $uid;
        } else {
            return false;
        }
    }
    
     /**
     * Storing new liste
     * return liste uid
     */
    public function storeLink($id_liste, $id_produit, $qte_produit) {
        $result = mysqli_query($this->db->connect(), "INSERT INTO associer (id_liste, id_produit, qte_produit) VALUES('$id_liste', '$id_produit', '$qte_produit')");
        // check for successful store
        if ($result) {
            // return user details
            return true;
        } else {
            return false;
        }
    }
    
    function customHash(){
        $chars = 6;
        $items = 'abcdefghijklmnpqrstuvwxyz0123456789';
        $output = '';
        $chars 	= (int)$chars;
        $nbr	= strlen($items);
        if($chars > 0 && $nbr > 0){
                for($i = 0; $i < $chars; $i++){
                        $output	.= $items[mt_rand(0,($nbr-1))];
                }
        }
        return $output;
    }
    

    
 
}
 
?>

