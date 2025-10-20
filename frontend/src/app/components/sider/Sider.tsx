"use client"
import { useState } from "react";
import { FaGaugeHigh, FaGear, FaListCheck, FaPowerOff, FaTableCellsLarge, FaTableList, FaUser, FaUserGear, FaUserGroup } from "react-icons/fa6";
import { SiderMenu } from "./SiderMenu";

export const Sider = (props: {
  activeSider: boolean;
  setActiveSider: (value: boolean) => void;
}) => {
  const menuList1 = [
    {
      name: "Tổng quan",
      link: "#",
      icon: FaGaugeHigh
    },
    {
      name: "Quản lý danh mục",
      link: "#",
      icon: FaTableCellsLarge
    },
    {
      name: "Quản lý tour",
      link: "#",
      icon: FaTableList
    },
    {
      name: "Quản lý đơn hàng",
      link: "#",
      icon: FaListCheck
    },
    {
      name: "Quản lý người dùng",
      link: "#",
      icon: FaUser
    },
    {
      name: "Thông tin liên hệ",
      link: "#",
      icon: FaUserGroup
    },
  ]

  const menuList2 = [
    {
      name: "Cài đặt chung",
      link: "#",
      icon: FaGear
    },
    {
      name: "Thông tin cá nhân",
      link: "#",
      icon: FaUserGear
    },
    {
      name: "Đăng xuất",
      link: "#",
      icon: FaPowerOff,
      isLogout: true
    }
  ]

  const { activeSider, setActiveSider } = props;
  const [activeMenu, setActiveMenu] = useState(menuList1[0].name);

  return (
    <>
      <nav className={`bg-[#FFFFFF] border-r border-[#E0E0E0] lg:w-[240px] w-[280px] lg:h-[calc(100vh-70px)] h-full fixed lg:top-[70px] top-0 left-0 overflow-y-auto [&::-webkit-scrollbar]:w-[3px] [&::-webkit-scrollbar-thumb]:bg-[#ddd] z-[999] lg:block ${activeSider ? 'block' : 'hidden'}`}>
        <SiderMenu
          menuList={menuList1}
          activeMenu={activeMenu}
          setActiveMenu={setActiveMenu}
        />
        <hr className="border border-[#E0E0E0] my-[16px] mx-0" />
        <SiderMenu
          menuList={menuList2}
          activeMenu={activeMenu}
          setActiveMenu={setActiveMenu}
        />
      </nav>
      <div
        className={`${activeSider ? 'lg:hidden block' : 'hidden'} fixed top-0 left-0 w-full h-full bg-[#0000008d] z-[998]`}
        onClick={() => setActiveSider(false)}
      >
      </div>
    </>
  )
}