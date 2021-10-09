@SuppressWarnings("unchecked")
//Negate warnings that the complier highlights
public class DynamicArray <T> implements Iterable<T> {
	//Test
	public static void main(String[] args) {
		DynamicArray<Integer> one = new DynamicArray<Integer>(2);
		one.add(1);
		one.add(2);
		System.out.print(one.toString());
	}
//<T> means that generic are available, the array created from this class can handle any type of data
//Iterable allows an object to be the target of an enhanced for loop
	private T[] arr;
	private int len = 0; //Length of the array that the user can see
	private int capacity = 0; //Actual back end length of the array
	
	//Constructor
	public DynamicArray(int capacity) {
		if (capacity < 0) throw new IllegalArgumentException("Illegal Capacity: " + capacity);
		//Ensures that the actual size of the array is greater than zero
		this.capacity = capacity;
		arr = (T[]) new Object[capacity];
		//Setting the data type of the array created to the generic type that the user has inputed
		//All other data types are children of the Object data type
	}
	
	//Constructor
	public DynamicArray() {
		this(16);
		//Initialize the size of the array to 16 
	}
	
	public int size() { return len;}
	
	public boolean isEmpty() { return size() == 0;}
	
	public T get(int index) { return arr[index];}
	
	public void set(int index,T elem) { arr[index] = elem; }
	
	//When emptying any datastructure set to null, 0 is still a form of data
	public void clear() {
		for(int i = 0; i < capacity;i++) {
			arr[i] = null;
			len = 0;
		}
	}
	
	public void add(T elem) {
		//Resize the array
		//If the length of the array is equal to or greater than the capacity 
		if(len + 1 >= capacity) {
			//If the array was cleared before
			if (capacity == 0) capacity = 1;
			//Otherwise double the size of the array
			else capacity *= 2;
			//Create an new array with the new capacity
			T[] new_arr = (T[]) new Object[capacity];
			//Copy the old values to the new array
			for (int i = 0;i < len;i ++) {
				new_arr [i] = arr[i];
			}
			//Set the new array to the array used by the code
			arr = new_arr;
		}
		//Add the new element to the array
		arr[len++] = elem;
	}
	//Remove at a particular index
	public T removeAt(int rm_index) {
		//Check if the index is valid
		if(rm_index >= len && rm_index < 0) throw new IndexOutOfBoundsException();
		//Grab the data value to be removed
		T data = arr[rm_index];
		//Create new array one size short due to the removed value
		T[] new_arr= (T[]) new Object[len - 1];
		//Copy old values apart from the highlighted or grabed value
		for(int i = 0, j = 0;i < len;i++,j++) {
			//At the specified index place the next value
			/*Int j creates the index of the  new array while i is for the old array
			 since the old array is bigger(by one) compared to the smaller array
			 the following situation must be fulfilled from the index of the skipped value to ensure data concurrency
			 old[n] = new[n-1] which means int the old array,an element in index 5 shouls be inserted in the new array
			 at index 4 */
			if(i == rm_index) j--;
			else new_arr[j] = arr[i];
			}
		//Set new array to the one used by the code
		arr = new_arr;
		// len-- gives value then decrements, --len decrements and gives decremented value
		capacity = --len;
		return data;
	}
	
	public boolean remove(Object obj) {
		for(int i = 0;i < len;i++) {
			if(arr[i].equals(obj)) {
				removeAt(i);return true;
			}
		}
		//If data not in array
		return false;
	}
	
	public int indexOf(Object obj) {
		for(int i = 0;i < len;i++) {
			if(arr[i].equals(obj))
				return i;
		}
		return -1;
	}
	
	public boolean contains(Object obj) {
		//indexOf() returns either an index(0,1) or -1
		//If and object exists an index is returned and the return value is true since the index isn't equal to -1
		//If an object does not exist -1 is returned and -1 != -1 is false
		return indexOf(obj) != -1;
	}
	
	//Iterator provides an abstraction over the iteration process, a for loop is still faster
	@Override
	public java.util.Iterator <T> iterator(){
		return new java.util.Iterator <T> () {
			int index = 0;
			//Checks if there is a next value, if index is less than len true is returned
			public boolean hasNext() { return index < len;}
			//Goes to the next value
			public T next() { return arr[index++]; }
		};
	}
	
	@Override
	public String toString() {
		if (len == 0) return "[]";
		else {
			StringBuilder sb = new StringBuilder(len).append("[");
			for(int i = 0; i < len-1;i++)
				sb.append(arr[i] + ",");
			//The toString() converts the StringBuilder to String
			return sb.append(arr[len - 1] + "]").toString();
		}
	}
}

