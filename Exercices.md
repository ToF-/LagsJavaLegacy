### Refactoring:
Améliorer la lisibilité et la modularité du programme

### Evolution: 
Rendre paramétrable le chemin d'accès au fichier des ordres 

### Correction: 
Erreur de comparaison des ordres 
un ordre qui commence le 2020340 et dure 50 jours doit chevaucher un orde commençant le 2021002
1;2020340;50;1000
2;2021001;40;2000

résultat attendu:2000
résultat obtenu: 3000

### Performance: 
Lorsque le fichier des ordres contient plus de 5000 ordres, le calcul se fige

