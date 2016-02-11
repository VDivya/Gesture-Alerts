package com.example.group1project;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import android.util.Log;
import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.ObservationVector;
import be.ac.ulg.montefiore.run.jahmm.OpdfMultiGaussianFactory;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationSequencesReader;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationVectorReader;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchLearner;
import be.ac.ulg.montefiore.run.jahmm.learn.KMeansLearner;

public class TestGesture {
	OpdfMultiGaussianFactory initFactoryPunch=null;
	Reader learnReaderPunch=null;
	List<List<ObservationVector>> learnSequencesPunch=null;
	KMeansLearner<ObservationVector> kMeansLearnerPunch=null;
	Hmm<ObservationVector> initHmmPunch=null;
	Hmm<ObservationVector> learntHmmPunch=null;
	Hmm<ObservationVector> learntHmmScrolldown=null;
	Hmm<ObservationVector> learntHmmSend=null;
	Hmm<ObservationVector> learntHmmEmer=null;
	Hmm<ObservationVector> learntHmmBack=null;
	File sdCard = Environment.getExternalStorageDirectory(); 
	File myDir = new File (sdCard.getAbsolutePath() + "/Data"); 

	// String root = Environment.getExternalStorageDirectory().toString();
	  //  File myDir = new File(root + "/Data");    
	   
public void	train() {
	 myDir.mkdirs();
	// Create HMM for punch gesture
	Boolean exception =false;
	int x=10;
	while(!exception){
	try{
   OpdfMultiGaussianFactory initFactoryPunch = new OpdfMultiGaussianFactory(
           3);
   
   Reader learnReaderPunch = new FileReader(new File (myDir, "hungry.seq"));
   List<List<ObservationVector>> learnSequencesPunch = ObservationSequencesReader
           .readSequences(new ObservationVectorReader(), learnReaderPunch);
   Log.i("Hunger File Readed : ",learnSequencesPunch.toString());
   
   //learnReaderPunch.close();

   KMeansLearner<ObservationVector> kMeansLearnerPunch = new KMeansLearner<ObservationVector>(
           x, initFactoryPunch, learnSequencesPunch);
   // Create an estimation of the HMM (initHmm) using one iteration of the
   // k-Means algorithm
   Hmm<ObservationVector> initHmmPunch = kMeansLearnerPunch.iterate();
   // Use BaumWelchLearner to create the HMM (learntHmm) from initHmm
   
   BaumWelchLearner baumWelchLearnerPunch = new BaumWelchLearner();
    this.learntHmmPunch = baumWelchLearnerPunch.learn(
           initHmmPunch, learnSequencesPunch);
   exception=true;
   System.out.println(x);
	  }
	  catch(Exception e){
		  x--;
		  //System.out.println(x);
		  
	  }

}
   // Create HMM for scroll-down gesture
	Boolean exception1 =false;
	int x1=10;
	while(!exception1){
	try{
   OpdfMultiGaussianFactory initFactoryScrolldown = new OpdfMultiGaussianFactory(
           3);

   Reader learnReaderScrolldown = new FileReader(new File (myDir, "stomp.seq"));
   List<List<ObservationVector>> learnSequencesScrolldown = ObservationSequencesReader
           .readSequences(new ObservationVectorReader(),
                   learnReaderScrolldown);
  // learnReaderScrolldown.close();
   Log.i("StompSmall File Readed : ",learnReaderScrolldown.toString());
   KMeansLearner<ObservationVector> kMeansLearnerScrolldown = new KMeansLearner<ObservationVector>(
           x1, initFactoryScrolldown, learnSequencesScrolldown);
   // Create an estimation of the HMM (initHmm) using one iteration of the
   // k-Means algorithm
   Hmm<ObservationVector> initHmmScrolldown = kMeansLearnerScrolldown
           .iterate();

   // Use BaumWelchLearner to create the HMM (learntHmm) from initHmm
   BaumWelchLearner baumWelchLearnerScrolldown = new BaumWelchLearner();
    this.learntHmmScrolldown = baumWelchLearnerScrolldown
           .learn(initHmmScrolldown, learnSequencesScrolldown);
    exception1=true;
    //System.out.println("here1");
    System.out.println(x1);
	  }
	  catch(Exception e){
		  x1--;
		  //System.out.println(x1);
		  
	  }
	}
   // Create HMM for send gesture
	Boolean exception2 =false;
	int x2=10;
	while(!exception2){
	try{
   OpdfMultiGaussianFactory initFactorySend = new OpdfMultiGaussianFactory(
           3);

   Reader learnReaderSend = new FileReader(new File (myDir, "thirsty.seq"));
   List<List<ObservationVector>> learnSequencesSend = ObservationSequencesReader
           .readSequences(new ObservationVectorReader(), learnReaderSend);
   Log.i("Thirsty File Readed : ",learnReaderSend.toString());
   learnReaderSend.close();

   KMeansLearner<ObservationVector> kMeansLearnerSend = new KMeansLearner<ObservationVector>(
           x2, initFactorySend, learnSequencesSend);
   // Create an estimation of the HMM (initHmm) using one iteration of the
   // k-Means algorithm
   Hmm<ObservationVector> initHmmSend = kMeansLearnerSend.iterate();

   // Use BaumWelchLearner to create the HMM (learntHmm) from initHmm
   BaumWelchLearner baumWelchLearnerSend = new BaumWelchLearner();
    this.learntHmmSend = baumWelchLearnerSend.learn(
           initHmmSend, learnSequencesSend);
    exception2=true;
    System.out.println(x2);
	  }
	  catch(Exception e){
		  x2--;
		  //System.out.println(x2);
		  
	  }
	}
	 // Create HMM for send gesture
		Boolean exception3 =false;
		int x3=10;
		while(!exception3){
		try{
	   OpdfMultiGaussianFactory initFactoryEmer = new OpdfMultiGaussianFactory(
	           3);

	   Reader learnReaderEmer = new FileReader(new File (myDir, "emergency1.seq"));
	   List<List<ObservationVector>> learnSequencesEmer = ObservationSequencesReader
	           .readSequences(new ObservationVectorReader(), learnReaderEmer);
	   Log.i("Emergency File Readed : ",learnReaderEmer.toString());
	   learnReaderEmer.close();

	   KMeansLearner<ObservationVector> kMeansLearnerEmer = new KMeansLearner<ObservationVector>(
	           x3, initFactoryEmer, learnSequencesEmer);
	   // Create an estimation of the HMM (initHmm) using one iteration of the
	   // k-Means algorithm
	   Hmm<ObservationVector> initHmmEmer = kMeansLearnerEmer.iterate();

	   // Use BaumWelchLearner to create the HMM (learntHmm) from initHmm
	   BaumWelchLearner baumWelchLearnerEmer = new BaumWelchLearner();
	    this.learntHmmEmer = baumWelchLearnerEmer.learn(
	           initHmmEmer, learnSequencesEmer);
	    exception3=true;
	    System.out.println(x3);
		  }
		  catch(Exception e){
			  x3--;
			  //System.out.println(x2);
			  
		  }
		}
		
		 // Create HMM for send gesture
		Boolean exception4 =false;
		int x4=10;
		while(!exception4){
		try{
	   OpdfMultiGaussianFactory initFactoryBack = new OpdfMultiGaussianFactory(
	           3);

	   Reader learnReaderBack = new FileReader(new File (myDir, "exit.seq"));
	   List<List<ObservationVector>> learnSequencesBack = ObservationSequencesReader
	           .readSequences(new ObservationVectorReader(), learnReaderBack);
	   Log.i("Back File Readed : ",learnReaderBack.toString());
	   learnReaderBack.close();

	   KMeansLearner<ObservationVector> kMeansLearnerBack = new KMeansLearner<ObservationVector>(
	           x4, initFactoryBack, learnSequencesBack);
	   // Create an estimation of the HMM (initHmm) using one iteration of the
	   // k-Means algorithm
	   Hmm<ObservationVector> initHmmBack = kMeansLearnerBack.iterate();

	   // Use BaumWelchLearner to create the HMM (learntHmm) from initHmm
	   BaumWelchLearner baumWelchLearnerBack = new BaumWelchLearner();
	    this.learntHmmBack = baumWelchLearnerBack.learn(
	           initHmmBack, learnSequencesBack);
	    exception4=true;
	    System.out.println(x4);
		  }
		  catch(Exception e){
			  x4--;
			  //System.out.println(x2);
			  
		  }
		}

}
	
public String test(File seqfilename) throws Exception{
    Reader testReader = new FileReader(new File (myDir, "learn.seq"));
    List<List<ObservationVector>> testSequences = ObservationSequencesReader
            .readSequences(new ObservationVectorReader(), testReader);
    testReader.close();
    Log.i("Text Readed : ",testSequences.toString());
    short gesture; // punch = 1, scrolldown = 2, send = 3
    double punchProbability, scrolldownProbability, sendProbability, emerProbability, backProbability;
    Log.i("Test gesture Method : ", "Entered");
    
    for (int i = 0; i < testSequences.size(); i++) {
    	 Log.i("seq file size : ", "one+");
        punchProbability = this.learntHmmPunch.probability(testSequences
                .get(i));
        Log.i("seq file size : ", "two+");
        //System.out.println(this.learntHmmPunch.probability(testSequences.get(i)));
        gesture = 1;
        //System.out.println(this.learntHmmScrolldown);
        scrolldownProbability = this.learntHmmScrolldown.probability(testSequences
                .get(i));
        Log.i("seq file size : ", "three+");
        if (scrolldownProbability > punchProbability) {
            gesture = 2;
        }
        sendProbability = this.learntHmmSend.probability(testSequences
                .get(i));
        Log.i("seq file size : ", "four+");
        //System.out.println(punchProbability +","+scrolldownProbability +","+sendProbability);
        if ((gesture == 1 && sendProbability > punchProbability)
                || (gesture == 2 && sendProbability > scrolldownProbability)) {
            gesture = 3;
        }
        
        emerProbability = this.learntHmmEmer.probability(testSequences
                .get(i));
        Log.i("seq file size : ", "five+");
        //System.out.println(punchProbability +","+scrolldownProbability +","+emerProbability);
        if ((gesture == 1 && emerProbability > punchProbability)
                || (gesture == 2 && emerProbability > scrolldownProbability) || (gesture == 3 && emerProbability > sendProbability)) {
            gesture = 4;
        }
        
        
       backProbability = this.learntHmmBack.probability(testSequences
                .get(i));
        Log.i("seq file size : ", "five+");
        //System.out.println(punchProbability +","+scrolldownProbability +","+emerProbability);
        if ((gesture == 1 && backProbability > punchProbability)
                || (gesture == 2 && backProbability > scrolldownProbability) || (gesture == 3 && backProbability > sendProbability)|| (gesture == 4 && backProbability > emerProbability)) {
            gesture = 5;
        }
        
        
        
        
        Log.i("probabilities", punchProbability + "   " + sendProbability  + "   " + scrolldownProbability);
        if (gesture == 1) {
        	System.out.println("This is a Hungry gesture");
        	return "hungry";
        } else if (gesture == 2) {
            System.out.println("This is a Game Play gesture");
        	return "game";
        } else if (gesture == 3) {
        	System.out.println("This is a Thirsty gesture");
        	return "thirsty";
        }
        else if (gesture == 4) {
        	System.out.println("This is a Emergency gesture");
        	return "emergency";
        }
        else if (gesture == 5) {
        	System.out.println("This is a Exit gesture");
        	return "exit";
        }else{
        	return "others";
        }
       
    }
//    Log.i("seq file size : ", i.toString());
	return "others";
}
 
} 