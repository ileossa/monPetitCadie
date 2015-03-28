<?php

if (isset($_POST['tag']) && !empty($_POST['tag'])) {
    // get tag
    $tag = $_POST['tag'];
 
    // include db handler
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();
 
    // response Array
    $response = array("tag" => $tag, "success" => 0, "error" => 0);
    $matched = false;
    // check for tag type
    if ($tag == 'login') {
        $matched = true;
        // Request type is check Login
        $name = $_POST['name'];
        $password = $_POST['password'];
 
        // check for user
        $user = $db->getUserByNameAndPassword($name, $password);
        if ($user != false) {
            // user found
            // echo json with success = 1
            $response["success"] = 1;
            $response["uid"] = $user["uid"];
            $response["user"]["name"] = $user["name"];
            $response["user"]["email"] = $user["email"];
            echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "Incorrect email or password!";
            echo json_encode($response);
        }
    }
    if ($tag == 'register') {
        $matched = true;
        // Request type is Register new user
        $name = $_POST['name'];
        $email = $_POST['email'];
        $password = $_POST['password'];
 
        // check if user is already existed
        if ($db->isUserExisted($name)) {
            // user is already existed - error response
            $response["error"] = 2;
            $response["error_msg"] = "User already existed";
            echo json_encode($response);
        } else {
            // store user
            $user = $db->storeUser($name, $email, $password);
            if ($user) {
                // user stored successfully
                $response["success"] = 1;
                $response["uid"] = $user["uid"];
                $response["user"]["name"] = $user["name"];
                $response["user"]["email"] = $user["email"];
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = 1;
                $response["error_msg"] = "Error occured in Registartion";
                echo json_encode($response);
            }
        }
    }
        if ($tag == 'reset') {
        $matched = true;
        // Request type is Register new user
        $name = $_POST['name'];
        $password=$db->customHash();
 
        // check if user is already existed
        if ($db->isUserExisted($name)) {
            // reset user
            $user = $db->resetUser($name, $password);
            if ($user) {
                // user stored successfully
                $response["success"] = 1;
                $response["newpassword"] = $password;
                $response["message"] = "Password reset";
                echo json_encode($response);
            } else {
                // user failed to reset
                $response["error"] = 1;
                $response["error_msg"] = "Error occured in reset";
                echo json_encode($response);
            }
        } else {

            $response["error"] = 2;
            $response["error_msg"] = "User not find";
            echo json_encode($response);
        }
    }
    
    if ($tag == 'produit') {
        $matched = true;
        // get product
        $product = $db->getProduct();
        if ($product) {          
            echo json_encode($db->utf8ize($product));
        } else {
            // user failed to store
            $response["error"] = 1;
            $response["error_msg"] = "Error occured in Product";
            echo json_encode($response);
        }
    }
    
    if ($tag == 'promotion') {
        $matched = true;
        // store product
        $promo = $db->getPromo();
        if ($promo) {
            // user stored successfully            
            echo json_encode($db->utf8ize($promo));
        } else {
            // user failed to store
            $response["error"] = 1;
            $response["error_msg"] = "Error occured in Product";
            echo json_encode($response);
        }
    } 
    if ($tag == 'getList') {
        $matched = true;
    }
    if ($tag == 'saveList') {
        $matched = true;
        $jsonlist = $_POST['list'];
        $list = json_decode($jsonlist, true);
        $uid= $db->storeListe($list['nom_liste'], $list['nom_utilisateur'], $list['modele']);
        if ($uid){
            $response["success"] = 1;
            $response["uid"] = $uid;
        }
        $jsonlinks = $_POST['links'];
        $links = json_decode($jsonlinks, true);
        $count = count($links);
        if($count>0){
            for ($i = 0; $i<=$count-1; $i++){
                $link = $links[$i];
                $db->storeLink($uid, $links[$i]['id_produit'], $links[$i]['qte_produit']);
            }  
        }  
        echo json_encode($response);
    }
    if (!$matched){
        echo "Invalid Request";
    }
} else {
    echo "Access Denied";
}
?>
