/* eslint-disable @typescript-eslint/no-explicit-any */
import Link from "next/link"
import { IconType } from "react-icons";
import { useRouter } from "next/navigation";

export const SiderMenu = (props: {
  menuList: Array<{
    name: string;
    link: string;
    icon: IconType;
    isLogout?: boolean;
  }>;
  activeMenu: string;
  setActiveMenu: (value: string) => void;
}) => {
  const { menuList, activeMenu, setActiveMenu } = props;
  const router = useRouter();

  const handleClick = (item: any) => {
    setActiveMenu(item.name);
    if (item.isLogout) {
      fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/logout`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include"
      })
        .then(res => res.json())
        .then(data => {
          console.log(data);
          if (!data.success) {
            alert("Đăng xuất thất bại");
          }
          if (data.success) {
            router.push("/login");
          }
        })
    }
  }

  return (
    <>
      {menuList && (
        <ul className="list-none p-0 my-[11px] mx-0">
          {menuList.map((item, index) => (
            <li
              key={index}
              className=""
            >
              <Link
                href={item.link}
                onClick={() => handleClick(item)}
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
      )}
    </>
  )
}