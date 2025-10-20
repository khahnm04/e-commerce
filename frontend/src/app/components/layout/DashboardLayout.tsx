"use client"
/* eslint-disable @next/next/no-img-element */
import Link from "next/link";
import { useState } from "react";
import { FaBars, FaGaugeHigh, FaGear, FaListCheck, FaPowerOff, FaTableCellsLarge, FaTableList, FaUser, FaUserGear, FaUserGroup } from "react-icons/fa6";

export const DashboardLayout = () => {
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

  const [activeMenu, setActiveMenu] = useState(menuList1[0].name);
  const [activeSider, setActiveSider] = useState(false);

  return (
    <>
      {/* Header */}
      <header className="bg-[#FFFFFF] border-b border-[#E0E0E0] h-[70px] fixed top-0 left-0 w-full z-[900] flex">
        {/* Logo */}
        <div className="sm:w-[240px] w-auto sm:ml-0 ml-[15px] flex items-center justify-center">
          <Link
            href="#"
            className="font-[800] text-[24px]"
          >
            <span className="text-[var(--color-primary)]">28</span>
            <span className="text-[var(--color-text)]">Admin</span>
          </Link>
        </div>
        {/* End Logo */}

        {/* Right */}
        <div className="flex-1 flex justify-end items-center sm:gap-[40px] gap-[20px] sm:mr-[30px] mr-[15px]">
          <div className="relative cursor-pointer">
            <img
              src="/assets/images/icon-bell.svg"
              alt=""
              className="w-[24px] h-auto"
            />
            <span className="h-[16px] min-w-[16px] bg-[#F93C65] font-[700] text-[12px] text-[#FFFFFF] py-0 px-[4px] rounded-[25px] absolute top-[-5px] right-[-4px]">
              6
            </span>
          </div>
          <div className="inline-flex items-center gap-[10px] cursor-pointer">
            <div className="w-[44px] h-[44px] rounded-[50%] overflow-hidden">
              <img
                src="assets/images/avatar.jpg"
                alt=""
                className="w-full h-full object-cover"
              />
            </div>
            <div className="flex-1">
              <div className="font-[700] text-[14px] text-[#404040] mb-[3px]">Le Van A</div>
              <div className="font-[600] text-[12px] text-[#565656]">Admin</div>
            </div>
          </div>
          <button
            className="lg:hidden inline-block text-[24px] bg-transparent p-0 border-0 cursor-pointer"
            onClick={() => setActiveSider(!activeSider)}
          >
            <FaBars />
          </button>
        </div>
        {/* End Right */}
      </header>
      {/* End Header */}

      {/* Sider */}
      <nav className={`bg-[#FFFFFF] border-r border-[#E0E0E0] lg:w-[240px] w-[280px] lg:h-[calc(100vh-70px)] h-full fixed lg:top-[70px] top-0 left-0 overflow-y-auto [&::-webkit-scrollbar]:w-[3px] [&::-webkit-scrollbar-thumb]:bg-[#ddd] z-[999] lg:block ${activeSider ? 'block' : 'hidden'}`}>
        <ul className="list-none p-0 my-[11px] mx-0">
          {menuList1.map((item, index) => (
            <li key={index} className="">
              <Link
                href={item.link}
                onClick={() => setActiveMenu(item.name)}
                className={`flex items-center gap-[10px] py-[12px] px-[16px] ml-[24px] mr-[10px] rounded-[6px] font-[600] text-[14px] text-[var(--color-text)] relative ${activeMenu === item.name
                  ? 'bg-[var(--color-primary)] text-white before:absolute before:left-[-24px] before:top-0 before:h-full before:w-[4.5px] before:rounded-[0_4px_4px_0] before:bg-[var(--color-primary)] before:content-[\'\']'
                  : 'text-[var(--color-text)]'}`}
              >
                <item.icon className="text-[16px]" /> {item.name}
              </Link>
            </li>
          ))}
        </ul>
        <hr className="border border-[#E0E0E0] my-[16px] mx-0" />
        <ul className="list-none p-0 my-[11px] mx-0">
          {menuList2.map((item, index) => (
            <li key={index} className="">
              <Link
                href={item.link}
                onClick={() => setActiveMenu(item.name)}
                className={`flex items-center gap-[10px] py-[12px] px-[16px] ml-[24px] mr-[10px] rounded-[6px] font-[600] text-[14px] relative 
                  ${activeMenu === item.name
                    ? 'bg-[var(--color-primary)] before:absolute before:left-[-24px] before:top-0 before:h-full before:w-[4.5px] before:rounded-[0_4px_4px_0] before:bg-[var(--color-primary)] before:content-[\'\']'
                    : ''
                  } 
                  ${activeMenu === item.name
                    ? 'text-white'
                    : item.isLogout
                      ? 'text-[#F93C65]'
                      : 'text-[var(--color-text)]'
                  }`}
              >
                <item.icon className="text-[16px]" /> {item.name}
              </Link>
            </li>
          ))}
        </ul>
      </nav>
      <div
        className={`${activeSider ? 'block' : 'hidden'} fixed top-0 left-0 w-full h-full bg-[#0000008d] z-[998]`}
        onClick={() => setActiveSider(false)}
      >
      </div>
      {/* End Sider */}
    </>
  );
}