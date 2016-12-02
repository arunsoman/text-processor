package com.flytxt.tp.processor.filefilter;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * For iterate over a FIltered files , The file array contains null values. 
 * While iterate using this class the null values will be skip.
 * 
 * @author shiju.john
 *
 * @param <T>
 */
public class FileIterator<T> implements Iterator<T>,Iterable<T>{
	
	private File[] filteredFiles;
	int cursor=0;
	int size=-1;
	boolean hasNext=false;
	
	public FileIterator(File[] filteredFiles) {
		this.filteredFiles = filteredFiles;
		if(null!=filteredFiles)
		size = filteredFiles.length;
	}

	@Override
	public boolean hasNext() {
		if(null!=filteredFiles && size>cursor && !hasNextNull()){
			hasNext= true;			
		}else{
			hasNext =false;			
		}	
		return hasNext;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T next() {		
		boolean notNull = false;
		do{	
				if(size>cursor){				
					File file = filteredFiles[cursor++];
					if(file!=null){
						return (T)file.toPath();
					}
				}else{
					notNull=true;
				}
				
			}while(!notNull);
		throw new NoSuchElementException();
	}
	
	
	/**
	 * For skip the null values while iterating. If the next value is a null value 
	 * then the pointer will point to the next not null value index.
	 * @return
	 */
	private boolean hasNextNull(){
		boolean notNull = false;
		int index = cursor;
		do{	
			if(size>index){				
				File file = filteredFiles[index++];
				if(file!=null){
					return false;
				}
				cursor=index;
			}else{
				notNull=true;
			}			
		}while(!notNull);
		
		return true;
	}

	@Override
	public Iterator<T> iterator() {
		return this;
	}

	

}
