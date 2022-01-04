package in.stjosephsacademy.abhiyush.customdatatypes;
public class StringList {
	private int len;
	private String[] list;
	
	public StringList() {
		len=0;
		list=new String[len];
	}
	
	public void add(String elem) {
		len+=1;
		String[] tmp_list=new String[len];
		for(int i=0;i<len-1;i++) {
			tmp_list[i]=list[i];
		}
		list=tmp_list;
		list[len-1]=elem;
	}
	
	public void delete(String elem) throws NoSuchElementException {
		int index=getIndex(elem);
		String[] tmp_list=new String[len-1];
		for(int i=0;i<index;i++) {
			tmp_list[i]=list[i];
		}
		for(int i=index;i<len-1;i++) {
			tmp_list[i]=list[i+1];
		}
		list=tmp_list;
		len--;
	}
	
	public String get(int index) {
		if(index<len)
			return list[index];
		else
			return null;
	}
	
	public int getIndex(String elem) throws NoSuchElementException {
		for(int i=0;i<len;i++) {
			if(list[i].equals(elem)) {
				return i;
			}
		}
		throw new NoSuchElementException();
	}
	
	public String[] toArray() {
		return list;
	}

	public int length() {
		return len;
	}
}
