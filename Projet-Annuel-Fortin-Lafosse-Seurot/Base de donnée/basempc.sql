-- phpMyAdmin SQL Dump
-- version 4.1.4
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Ven 11 Juillet 2014 à 17:31
-- Version du serveur :  5.6.15-log
-- Version de PHP :  5.5.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `basempc`
--

-- --------------------------------------------------------

--
-- Structure de la table `associer`
--

CREATE TABLE IF NOT EXISTS `associer` (
  `id_liste` int(4) NOT NULL,
  `id_produit` int(4) NOT NULL,
  `qte_produit` varchar(50) NOT NULL,
  PRIMARY KEY (`id_liste`,`id_produit`),
  KEY `FK_Associer_id_produit` (`id_produit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `liste`
--

CREATE TABLE IF NOT EXISTS `liste` (
  `uid` int(4) NOT NULL AUTO_INCREMENT,
  `id_liste` int(4) NOT NULL,
  `nom_liste` char(50) DEFAULT NULL,
  `nom_utilisateur` varchar(50) NOT NULL,
  `modele` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE IF NOT EXISTS `produit` (
  `id_produit` int(4) NOT NULL AUTO_INCREMENT,
  `nom_produit` char(50) DEFAULT NULL,
  `parent` int(4) DEFAULT NULL,
  `favori` tinyint(1) DEFAULT '0',
  `picture` char(100) DEFAULT NULL,
  PRIMARY KEY (`id_produit`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1414 ;

--
-- Contenu de la table `produit`
--

INSERT INTO `produit` (`id_produit`, `nom_produit`, `parent`, `favori`, `picture`) VALUES
(1, 'Boissons', 0, 0, NULL),
(2, 'Fruits', 0, 0, NULL),
(3, 'Legumes', 0, 0, NULL),
(4, 'Viandes', 0, 0, NULL),
(5, 'Produits laitiers', 0, 0, NULL),
(6, 'Poissons', 0, 0, NULL),
(7, 'Petit-déjeuners', 0, 0, NULL),
(110, 'Eau', 1, 0, NULL),
(111, 'Soda', 1, 0, NULL),
(112, 'Jus de Fruits', 1, 0, NULL),
(113, 'Alcool', 1, 0, NULL),
(114, 'Energy Drink', 1, 0, NULL),
(120, 'Pomme', 2, 0, NULL),
(121, 'Poires', 2, 0, NULL),
(122, 'Fraises', 2, 0, NULL),
(123, 'Annanas', 2, 0, NULL),
(124, 'Bananes', 2, 0, NULL),
(125, 'Kiwi', 2, 0, NULL),
(130, 'Carottes', 3, 0, NULL),
(131, 'Tomates', 3, 0, NULL),
(132, 'Haricots verts', 3, 0, NULL),
(133, 'Salade', 3, 0, NULL),
(134, 'Poireaux', 3, 0, NULL),
(140, 'Volailles', 4, 0, NULL),
(141, 'Charcuteries', 4, 0, NULL),
(142, 'Viandes de boeuf', 4, 0, NULL),
(143, 'Viandes de porc', 4, 0, NULL),
(150, 'Beurres', 5, 0, NULL),
(151, 'Crèmes', 5, 0, NULL),
(152, 'Laits', 5, 0, NULL),
(153, 'Yaourts', 5, 0, NULL),
(160, 'Saumons', 6, 0, NULL),
(161, 'Truites', 6, 0, NULL),
(162, 'Thons', 6, 0, NULL),
(163, 'Sardines', 6, 0, NULL),
(170, 'Céréales', 7, 0, NULL),
(171, 'Confitures', 7, 0, NULL),
(172, 'Pâtes à tartiner', 7, 0, NULL),
(173, 'Chocolats en poudre', 7, 0, NULL),
(1400, 'Poulet', 140, 0, NULL),
(1401, 'Dinde', 140, 0, NULL),
(1402, 'Canard', 140, 0, NULL),
(1410, 'Jambon', 141, 0, NULL),
(1411, 'Rillettes', 141, 0, NULL),
(1412, 'Saucisses', 141, 0, NULL),
(1413, 'Saucissons', 141, 0, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `promotion`
--

CREATE TABLE IF NOT EXISTS `promotion` (
  `id_promo` int(4) NOT NULL AUTO_INCREMENT,
  `nom_mag` char(50) NOT NULL,
  `desc_promo` char(150) NOT NULL,
  `date_debut` date NOT NULL,
  `date_fin` date NOT NULL,
  PRIMARY KEY (`id_promo`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Contenu de la table `promotion`
--

INSERT INTO `promotion` (`id_promo`, `nom_mag`, `desc_promo`, `date_debut`, `date_fin`) VALUES
(1, 'Auchan', 'Promo sur gateaux OREO', '2014-07-01', '2014-07-12'),
(2, 'Carrefour', 'Promo sur lait CANDIA', '2014-07-01', '2014-07-12'),
(3, 'Aldi', 'Promo sur jus de fuit', '2014-07-02', '2014-07-10'),
(6, 'esgi', 'promotion Platon', '2014-01-14', '2014-07-01');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE IF NOT EXISTS `utilisateur` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `encrypted_password` varchar(80) NOT NULL,
  `salt` varchar(10) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`uid`, `name`, `email`, `encrypted_password`, `salt`) VALUES
(1, 'amnesys', 'amnesys@toto.com', 'PFRxsin30kUc4Xq1HoKPM3BszNdmMzNmN2FlODlj', 'f33f7ae89c'),
(2, 'vincent', 'swip.delta@gmail.com', 'tKDOIGZBEWk7tlbYoYcaXp9cBN03NzAxZDZhMTc0', '7701d6a174'),
(3, 'toto', 'toto@toto.com', 'Yb8SG940/htzH9w6z6yINFeL6KphZjAxN2E3YmJi', 'af017a7bbb'),
(4, 'admin', 'admin@toto.com', 'Vijpjnbguo5ERfil7y/pV+EkVjQwYzA1OWU4MjM5', '0c059e8239'),
(5, 'valentin', 'valentin@toto.com', 'apX0BpRyOA5PFHnbWW5k+sWjcYFkMDI3MDE0NGU1', 'd0270144e5'),
(6, 'mpc', 'vincnet.iam@gmail.com', 'WiKlXdxQiI3PfQwuIrdxNv7NdjswNTY0NTM2ZGIz', '0564536db3'),
(7, 'totohhhhjhh', 'toto@toto.frfg', 'ILm504LIa7BRXF/K9K+/vFmepZMwMGFlNzRlNzI5', '00ae74e729'),
(8, '"', '', 'BRRAzMhZjGo739bhTUalaWw3xeY5NzYzZTYwM2M4', '9763e603c8');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `associer`
--
ALTER TABLE `associer`
  ADD CONSTRAINT `FK_Associer_id_produit` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id_produit`);
--
-- Base de données :  `exemplejdbc`
--

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id`, `nom`) VALUES
(1, 'Belard'),
(2, 'Fortin'),
(3, 'Seurot'),
(4, 'Lafosse');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
