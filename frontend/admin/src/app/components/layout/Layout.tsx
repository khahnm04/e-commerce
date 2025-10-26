"use client"
import { useState } from "react";
import { Header } from "../header/Header";
import { Sider } from "../sider/Sider";

export const Layout = () => {
  const [activeSider, setActiveSider] = useState(false);

  return (
    <>
      {/* Header */}
      <Header
        activeSider={activeSider}
        setActiveSider={setActiveSider}
      />
      {/* End Header */}

      {/* Sider */}
      <Sider
        activeSider={activeSider}
        setActiveSider={setActiveSider}
      />
      {/* End Sider */}
    </>
  );
}