package com.prj.edu.dao;

import java.util.ArrayList;

import com.prj.edu.dto.BoardDTO;
import com.prj.edu.dto.CartDTO;


public interface CartDAO {

	int cartDel(String cart_idx);

	void addcart(CartDTO cart);

	String checkcart(CartDTO cart);

	ArrayList<BoardDTO> cartList(int cnt, int offset, String id);

	int allCount(String id);

}
