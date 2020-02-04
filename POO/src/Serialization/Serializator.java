package Serialization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Serializator {
	
	public File f ;
	
	public Serializator(File f) {
		this.f = f ;
		try {
			// ouverture d'un flux de sortie vers le fichier "personne.serial"
			FileOutputStream fos = new FileOutputStream(f.getName() + ".serial");

			// création d'un "flux objet" avec le flux fichier
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			
			try {
                // sérialisation : écriture de l'objet dans le flux de sortie
                oos.writeObject(f); 
                // on vide le tampon
                oos.flush();
                System.out.println(f + " a ete serialise");
            } finally {
                //fermeture des flux
                try {
                    oos.close();
                } finally {
                    fos.close();
                }
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main (String[] args) {
		File f = new File("file.txt") ;
		Serializator s = new Serializator(f);
	}
	
}
