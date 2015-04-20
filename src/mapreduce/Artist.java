package mapreduce;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.WritableUtils;

import java.math.BigInteger;

import  org.apache.hadoop.io.WritableComparable;

public class Artist  implements WritableComparable<Artist>{
	
	public String name;
	public String date;
	
	public Artist(String name,String date){
		this.name = name;
		this.date = date;
	}
	
	public Artist() {
		this("", "");
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		name = WritableUtils.readString(in);
		date = WritableUtils.readString(in);
		
	}
	@Override
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, name);
		WritableUtils.writeString(out, date);
	}
	
	@Override
	public int compareTo(Artist other) {
		if (( name.compareTo(other.name) == 0 && date.compareTo(other.date) == 0 )
	                || ( name.compareTo(other.date) == 0 && name.compareTo(other.date) == 0 ) ) {
	    return 0;
	    } else {
	    	int cmp = name.compareTo( other.name );

	         if (cmp != 0) {
	        	 return cmp;
	          }
	    return date.compareTo( other.date );
	        }
	    }

    @Override
    public String toString() {
        return name + "\t" + date;
    }
    
    @Override
    public int hashCode() {
        BigInteger bA = BigInteger.ZERO;
        BigInteger bB = BigInteger.ZERO;

        for(int i = 0; i < name.length(); i++) {
            bA = bA.add(BigInteger.valueOf(127L).pow(i+1).multiply(BigInteger.valueOf(name.codePointAt(i))));
        }

        for(int i = 0; i < date.length(); i++) {
            bB = bB.add(BigInteger.valueOf(127L).pow(i+1).multiply(BigInteger.valueOf(date.codePointAt(i))));
        }

        return bA.multiply(bB).intValue();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Artist) {
            Artist other = (Artist) o;

            boolean result = (name.compareTo(other.name) == 0 && date.compareTo(other.date) == 0 )
                    || ( name.compareTo(other.date) == 0 && date.compareTo(other.name) == 0 );

            return result;
        }
        return false;
    }

}
