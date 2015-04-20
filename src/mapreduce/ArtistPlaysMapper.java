package mapreduce;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArtistPlaysMapper extends Mapper<Object, Text, Artist, IntWritable> {

    private final static IntWritable plays = new IntWritable();
    
    //the stop words list should ideally come as an input file as well but not assuming that: its not part of problem statement 
	private Set<String> stopWordList = new HashSet<String>(Arrays.asList("the", "a","for","of", "on"));
	
	//holds map of normalized name to actual name of artist
	private Map<String,String> artistNameMap = new HashMap<String,String>();
	private BufferedReader fis;
	
	protected void setup(Context context) throws java.io.IOException,
			InterruptedException {
 
		try {

			Path[] artistNameFiles = new Path[0];
			artistNameFiles = context.getLocalCacheFiles();
			System.out.println(artistNameFiles.toString());
			if (artistNameFiles != null && artistNameFiles.length > 0) {
				for (Path artistNameFile : artistNameFiles) {
					readArtistNameFile(artistNameFile);
				}
			}
			
		} catch (IOException e) {
			System.err.println("Exception reading stop word file: " + e);
 
		}
	    super.setup(context);
 
	}
	
	/*
	 * Method to read the artist name file and normalize
	 */
	private void readArtistNameFile(Path artistNameFile) {
		try {
			fis = new BufferedReader(new FileReader(artistNameFile.toString()));
			String artistName = null;
			while ((artistName = fis.readLine()) != null) {				
				artistNameMap.put(normalizeArtistName(artistName),artistName);
			}
		} catch (IOException ioe) {
			System.err.println("Exception while reading artist name file '"
					+ artistNameFile + "' : " + ioe.toString());
		}
	}
	

    @Override
    public void map(Object key, Text value, Context output) throws IOException,
            InterruptedException {

        String[] words = value.toString().split("\t");
        
        if(words == null || words.length != 3 || words[0] == null ||  words[1] == null ||  words[2] == null){
        	return;
        }
        
        String name = normalizeArtistName(words[0]);
        
        if(artistNameMap.get(name) == null){
        	return;
        }
                     
        Long unixSeconds = Long.valueOf(words[1]);
        Date date = new Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String formattedDate  = sdf.format(date);      
        
        plays.set(Integer.valueOf(words[2]));
        
        output.write(new Artist(artistNameMap.get(name),formattedDate), plays);

    }

	private String normalizeArtistName(String input) {
	     //Split artist name words by space
		StringBuilder normalizedArtistName = new StringBuilder();
        String [] words = input.split(" ");
        for(String word: words){
            String alphanumericWord = word.replaceAll("[^\\w\\s]", "");
            if(!stopWordList.contains(alphanumericWord.toLowerCase())){
            	normalizedArtistName.append(alphanumericWord+" ");
            }       	
        }
		return normalizedArtistName.toString().trim();
	}
}