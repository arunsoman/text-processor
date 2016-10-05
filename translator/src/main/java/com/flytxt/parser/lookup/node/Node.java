package com.flytxt.parser.lookup.node;

public class Node<T> {
	public final Node<T>[] nodes = new Node[122];
	private boolean isLeafNode;
	private T value;
	
	public void add(byte[] key,  T value){
		Node<T> n = this;
		Node<T> pre = this;
		for(int i :key){
			pre = n;
			n = n.nodes[i];
			if(n == null){
				pre.nodes[i] = new Node<T>();
				n = pre;
			}
		}
		n.isLeafNode = true;
		n.value = value;
	}
	public T traverse(byte[] search){
		Node<T> n = this;
		for(int aChar: search){
			n = n.nodes[aChar];
			if(n == null){
				return null;
			}
			if(n.isLeafNode){
				return value;
			}
		}
		return null;
	}
	
}
