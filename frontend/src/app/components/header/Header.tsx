"use client"
/* eslint-disable @next/next/no-img-element */
import Link from "next/link"
import { FaBars } from "react-icons/fa6"

export const Header = (props: {
  activeSider: boolean;
  setActiveSider: (value: boolean) => void;
}) => {
  const { activeSider, setActiveSider } = props;

  return (
    <>
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
    </>
  )
}