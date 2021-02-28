package com.tof.app;

import java.util.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;

class LagsService {
        private ArrayList<Ordre> listOrdre = new ArrayList<Ordre>();

        // lit le fihier des ordres et calcule le CA
        public void getFichierOrder(String fileName)
        {
            try{

            for (String line : Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8)) {
                String[] champs = line.split(";");
                String chp1 = champs[0];
                int chp2 = Integer.parseInt(champs[1]);
                int champ3 = Integer.parseInt(champs[2]);
                double chp4 = Double.parseDouble(champs[3]);
                Ordre ordre = new Ordre(chp1, chp2, champ3, chp4);
                listOrdre.add(ordre);

        }
            }
            catch (IOException e)
            {
                System.out.println("FICHIER ORDRES.CSV NON TROUVE. CREATION FICHIER.");
                writeOrdres(fileName);
            }
        }

        // écrit le fichier des ordres
        void writeOrdres(String nomFich)
        {
            List<String> lines = new ArrayList<String>();
            for(int i=0; i<listOrdre.size(); i++) {
                Ordre ordre = listOrdre.get(i);
                String ligneCSV = new String();
                ligneCSV = ordre.getId() + ";" + Integer.toString(ordre.getDebut()) +";"+Integer.toString(ordre.getDuree())+";"+Double.toString(ordre.prix());
                lines.add(ligneCSV);
                }
            try{
                Files.write(Paths.get(nomFich), lines,StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
            }
            catch (IOException e)
            {
                System.out.println("PROBLEME AVEC FICHIER");
            }
        }


        // affiche la liste des ordres
        public void liste()
        {
            Collections.sort(listOrdre, new Comparator<Ordre>() {
                  @Override
                     public int compare(Ordre o1, Ordre o2) {
                                return o1.getDebut() - o2.getDebut(); // use your logic, Luke
                                   }
            });
            System.out.println("LISTE DES ORDRES\n");
            System.out.format("%8s %8s %5s %13s", "ID", "DEBUT", "DUREE", "PRIX\n");
            System.out.format("%8s %8s %5s %13s", "--------", "-------", "-----", "----------\n");
            for(int i=0; i<listOrdre.size(); i++) {
                Ordre ordre = listOrdre.get(i);
                afficherOrdre(ordre);
            }
            System.out.format("%8s %8s %5s %13s", "--------", "-------", "-----", "----------\n");
        }

        public void afficherOrdre(Ordre ordre)
        {
            System.out.format("%8s %8d %5d %10.2f\n", ordre.getId(), ordre.getDebut(), ordre.getDuree(), ordre.prix());

        }
        // Ajoute un ordre; le CA est recalculé en conséquence
        public void ajouterOrdre()
        {
            System.out.println("AJOUTER UN ORDRE");
            System.out.println("FORMAT = ID;DEBUT;FIN;PRIX");
            String line = System.console().readLine().toUpperCase();
            String[] champs = line.split(";");
            String chp1 = champs[0];
            int chp2 = Integer.parseInt(champs[1]);
            int champ3 = Integer.parseInt(champs[2]);
            double chp4 = Double.parseDouble(champs[3]);
            Ordre ordre = new Ordre(chp1, chp2, champ3, chp4);
            listOrdre.add(ordre);
            writeOrdres("ordres.csv");
        }
        // MAJ du fichier
        public void suppression()
        {
            System.out.println("SUPPRIMER UN ORDRE");
            System.out.println("ID:");
            String id = System.console().readLine().toUpperCase();
            for (Iterator<Ordre> iter = listOrdre.listIterator(); iter.hasNext(); ) {
                    Ordre o = iter.next();
                        if (o.getId().equals(id)) {
                                    iter.remove();
                                        }
            }
            writeOrdres("ORDRES.CSV");
        }

        private double ca(List<Ordre> ordres, boolean debug)
        {
            // si aucun ordre, job done, TROLOLOLO..
            if (ordres.size() == 0)
                return 0.0;
            Ordre order = ordres.get(0);
            // attention ne marche pas pour les ordres qui depassent la fin de l'année 
            // voir ticket PLAF nO 4807 
            List<Ordre> liste = new ArrayList<Ordre>();
            for (Iterator<Ordre> iter = listOrdre.listIterator(); iter.hasNext(); ) {
                    Ordre o = iter.next();
                        if (o.getDebut()>=order.getDebut() + order.getDuree()) {
                                    liste.add(o);
                                        }
            }
            List<Ordre> liste2 = new ArrayList<Ordre>();
            for(int i=1; i<ordres.size(); i++) {
                liste2.add(ordres.get(i));
            }
            double ca = order.prix()+ ca(liste, debug);
            // Lapin compris?
            double ca2 = ca(liste2, debug);
            if(debug) {
                System.out.format("%10.2f\n", Math.max(ca, ca2));
            }
            else
                System.out.print(".");
            return Math.max(ca, ca2); // LOL
        }




        public void calculerLeCA(boolean debug)
        {
            System.out.println("CALCUL CA..");
            Collections.sort(listOrdre, new Comparator<Ordre>() {
                  @Override
                     public int compare(Ordre o1, Ordre o2) {
                                return o1.getDebut() - o2.getDebut(); // use your logic, Luke
                                   }
            });
            double ca = ca(listOrdre, debug);
            System.out.format("CA: %10.2f\n", ca);
        }
}

