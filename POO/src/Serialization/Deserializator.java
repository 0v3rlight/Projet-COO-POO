package Serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Deserializator {
	
	public File f = null ;
	
	public Deserializator(String filename) {
		try {
			// ouverture d'un flux d'entrée depuis le fichier "personne.serial"
            FileInputStream fis = new FileInputStream(filename);
            // création d'un "flux objet" avec le flux fichier
            ObjectInputStream ois= new ObjectInputStream(fis);
			
            f = new File(filename.replace(".serial",""));
            
            // ouverture d'un flux de sortie vers un nouveau fichier
            FileOutputStream fos = new FileOutputStream(f);

            // création d'un "flux objet" avec le flux fichier
         	ObjectOutputStream oos= new ObjectOutputStream(fos);
            
            try {
                // désérialisation : lecture de l'objet depuis le flux d'entrée
                f = (File) ois.readObject();
                // sérialisation : écriture de l'objet dans le flux de sortie
                oos.writeObject(f); 
                // on vide le tampon
                oos.flush();
            } finally {
                // on ferme les flux
                try {
                    ois.close();
                } finally {
                    fis.close();
                }
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        if(f != null) {
            System.out.println(f + " a ete deserialise");
        }
		
	}
	
	public static void main (String[] args) {
		Deserializator s = new Deserializator("file.txt.serial");
	}
	
}
