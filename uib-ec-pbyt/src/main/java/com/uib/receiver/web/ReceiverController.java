package com.uib.receiver.web;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uib.base.BaseController;
import com.uib.member.entity.MemMember;
import com.uib.member.entity.MemReceiver;
import com.uib.receiver.service.ReceiverService;



@Controller
@RequestMapping("/receiver")
public class ReceiverController extends BaseController{
	
	@Autowired
	private ReceiverService receiverService;
	
	@Value("${easypayicpURL}")
	private String EASYPAYICP_URL;
	
	

	
	/**
	 * 修改收货地址
	 * @param id
	 */
	@RequestMapping("/update")
	public String update(String id){
		try {
			receiverService.update(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/f/member/myAddress";
	}
	
	/**
	 * 删除收货地址
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete(String id){
		try {
			receiverService.delete(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/f/member/myAddress";
	}
	
	/**
	 * 添加收货地址
	 * @param id
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, HttpServletResponse response,MemReceiver memReceiver){
		HttpSession session = request.getSession();
		try {
			MemMember member = (MemMember) session.getAttribute("member");
			memReceiver.setId(UUID.randomUUID().toString());
			memReceiver.setMemMember(member);
			Timestamp d = new Timestamp(System.currentTimeMillis()); 
			memReceiver.setCreateDate(d);
			memReceiver.setUpdateDate(d);
			memReceiver.setDelFlag("0");
			receiverService.save(memReceiver);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/f/member/myAddress";
	}
}
