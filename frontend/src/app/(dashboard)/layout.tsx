import { Layout } from "../components/layout/Layout";

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <>
      <Layout />

      <div className="absolute top-[70px] lg:left-[240px] left-0 lg:w-[calc(100%-240px)] w-full min-h-[calc(100vh-70px)] 
        bg-[#F4F5F9] py-[30px] sm:px-[30px] px-[15px]">
        {children}
      </div>
    </>
  );
}
