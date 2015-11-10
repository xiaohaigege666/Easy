package com.thhh.easy.goods.action;

import java.util.List;

import com.thhh.easy.entity.Orderdetail;
import com.thhh.easy.entity.Orders;
import com.thhh.easy.goods.service.IOrdersService;
import com.thhh.easy.util.Constant;
import com.thhh.easy.util.MyUtil;


public class AndroidOrdersAction {

	private IOrdersService ordersService ;
	private int pageIndex; // 当前页数
	private int rowCount; // 每页显示条数
	
	private Orders orders ;			//订单对象
	private Orderdetail orderdetail ;	//订单详情对象
	private List<Orderdetail> listOrderdetail ;	//订单详情集合对象
	

	/**
	 * 购买商品
	 */
	public void buy(){
//		orders = new Orders() ;
//		Users u = new Users() ;
//		u.setId(1) ;
//		orders.setUsers(u) ;
//		orders.setDates(new Date()) ;
//		orders.setStates("0") ;
//		orders.setTake("18:00") ;
//		orders.setAmount(100.0f) ;
//		orders.setAllDeposit(200.0f) ;
//		Shop shop = new Shop() ;
//		shop.setId(1) ;
//		orders.setShop(shop) ;
//		
//		listOrderdetail = new ArrayList<Orderdetail>() ;
//		orderdetail = new Orderdetail() ;
//		Goods g = new Goods();
//		g.setId(1) ;
//		orderdetail.setGoods(g) ;
//		orderdetail.setAccount(2) ;
//		orderdetail.setAmount(4.1f) ;
//		orderdetail.setAllDeposit(0.3f) ;
//		listOrderdetail.add(orderdetail) ;
//		
//		orderdetail = new Orderdetail() ;
//		Goods g2 = new Goods();
//		g2.setId(1) ;
//		orderdetail.setGoods(g2) ;
//		orderdetail.setAccount(1) ;
//		orderdetail.setAmount(4.1f) ;	//拍下时的价格
//		orderdetail.setAllDeposit(0.3f) ;
//		listOrderdetail.add(orderdetail) ;
		
		if(orders == null || orders.getUsers() == null || orders.getShop() == null ||
				orders.getUsers().getId() == null || orders.getShop().getId() == null){
			MyUtil.sendString(Constant.STRING_0) ;
			return ;
		}
		if(listOrderdetail == null || listOrderdetail.size() <= 0){
			MyUtil.sendString(Constant.STRING_0) ;
			return ;
		}
		
		orders.setShop(ordersService.findShop(orders.getShop().getId())) ;
		orders.setUsers(ordersService.findUser(orders.getUsers().getId())) ;
		int orderId = ordersService.saveOrders(orders) ;
		orders = ordersService.findOrders(orderId) ;
		for (Orderdetail ord : listOrderdetail) {
			orderdetail = ord ;
			orderdetail.setOrders(orders) ;
			ordersService.saveOrderdetatil(orderdetail) ;
		}
		MyUtil.sendString(Constant.STRING_1) ;
		
		orders = null ;
		orderdetail = null ;
		listOrderdetail = null ;
		
	}
	
	/**
	 * 查看用户订单
	 */
	public void seeOrders(){
		if(orders == null || orders.getUsers() == null ||
				orders.getUsers().getId() == null){
			MyUtil.sendString(Constant.STRING_0) ;
			return ;
		}
		if (pageIndex == 0 || rowCount == 0) {
			setPageIndex(Constant.DEFAULT_PAGE);
			setRowCount(Constant.DEFAULT_PAGE_SIZE);
		}
		List<Orders> listOrders = ordersService.findOrdersByUserId(pageIndex,rowCount,orders.getUsers().getId()) ;
		MyUtil.sendString(listOrders) ;
		orders = null ;
	}
	
	/**
	 * 查看订单详情
	 */
	public void ordersInfo(){
		if(orderdetail == null || orderdetail.getOrders() == null ||
				orderdetail.getOrders().getId() == null){
			MyUtil.sendString(Constant.STRING_0) ;
			return ;
		}
		if (pageIndex == 0 || rowCount == 0) {
			setPageIndex(Constant.DEFAULT_PAGE);
			setRowCount(Constant.DEFAULT_PAGE_SIZE);
		}
		List<Orderdetail> listOrderdetail = ordersService.findOrderdetailByOrderId(pageIndex,rowCount,orderdetail.getOrders().getId()) ;
		MyUtil.sendString(listOrderdetail) ;
		orderdetail = null ;
	}

	public IOrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public Orderdetail getOrderdetail() {
		return orderdetail;
	}

	public void setOrderdetail(Orderdetail orderdetail) {
		this.orderdetail = orderdetail;
	}

	public List<Orderdetail> getListOrderdetail() {
		return listOrderdetail;
	}

	public void setListOrderdetail(List<Orderdetail> listOrderdetail) {
		this.listOrderdetail = listOrderdetail;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
}
