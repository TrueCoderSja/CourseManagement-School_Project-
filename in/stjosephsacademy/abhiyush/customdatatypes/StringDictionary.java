package in.stjosephsacademy.abhiyush.customdatatypes;
public class StringDictionary {
    private int len;
    private String[] keys, values;
    
    public StringDictionary() {
        len=0;
        keys=new String[len];
        values=new String[len];
    }
    
    public void add(String key, String value) {
        boolean replaced=false;
        len+=1;
        String[] tmp_keys=new String[len];
        String[] tmp_values=new String[len];
        for(int i=0;i<len-1;i++) {
            if(keys[i].equals(key)) {
                values[i]=value;
                replaced=true;
                len-=1;
                break;
            }
            tmp_keys[i]=keys[i];
            tmp_values[i]=values[i];
        }
        if(!replaced) {
            keys=tmp_keys;
            values=tmp_values;
            keys[len-1]=key;
            values[len-1]=value;
        }
    }
    
    public String getValue(String key) throws NoSuchElementException {
        for(int i=0;i<len;i++) {
            if(keys[i].equals(key))
                return values[i];
        }
        throw new NoSuchElementException();
    }
    
    public void deleteEntry(String key) throws NoSuchElementException {
        int index=-1;
        String[] tmp_keys=new String[len-1];
        String[] tmp_values=new String[len-1];
        for(int i=0;i<len;i++) {
            if(keys[i].equals(key)) {
                index=i;
                break;
            }
        }
        if(index==-1)
            throw new NoSuchElementException();
        for(int i=0;i<index;i++) {
            tmp_keys[i]=keys[i];
            tmp_values[i]=values[i];
        }
        for(int i=index;i<len-1;i++) {
            tmp_keys[i]=keys[i+1];
            tmp_values[i]=values[i+1];
        }
        keys=tmp_keys;
        values=tmp_values;
        len--;
    }
    
    public String[] getKeys() {
        return keys.clone();
    }
}
