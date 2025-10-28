"use client"
import "filepond/dist/filepond.min.css";
import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css';
import Link from "next/link";
import { useFilePond } from "@/hooks/useFilePond";
import { useTinyMCE } from "@/hooks/useTinyMCE";

export default function CategoryPage() {

  const { TinyMCEEditorComponent, editorRef } = useTinyMCE();

  useFilePond();

  return (
    <>
      <h1 className="mt-0 mb-[30px] font-bold text-[32px] text-[var(--color-text)]">
        Tạo danh mục
      </h1>

      <div className="bg-white border border-[#B9B9B9] rounded-[14px] md:p-[50px] p-[20px]">
        <form
          id="category-create-form"
          className="grid md:grid-cols-2 grid-cols-1 gap-[30px]"
        >
          <div className="inner-group relative">
            <label
              className="inner-label block font-[600] text-[14px] text-[#606060] mb-[11px]"
              htmlFor="name"
            >
              Tên danh mục
            </label>
            <input
              type="text"
              name="name"
              id="name"
              className="bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] font-[500] text-[14px] text-[var(--color-text)] w-full h-[52px]"
            />
          </div>
          <div className="inner-group relative">
            <label
              className="inner-label block font-[600] text-[14px] text-[#606060] mb-[11px]"
              htmlFor="parent"
            >
              Danh mục cha
            </label>
            <select
              name="parent"
              id="parent"
              className="bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] font-[500] text-[14px] text-[var(--color-text)] w-full h-[52px]"
            >
              <option value="">
                -- Chọn danh mục --
              </option>
              <option value="danh-muc-1">
                Danh mục 1
              </option>
              <option value="danh-muc-2">
                Danh mục 2
              </option>
            </select>
          </div>
          <div className="inner-group relative">
            <label
              className="inner-label block font-[600] text-[14px] text-[#606060] mb-[11px]"
              htmlFor="position"
            >
              Vị trí
            </label>
            <input
              type="text"
              name="position"
              id="position"
              className="bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] font-[500] text-[14px] text-[var(--color-text)] w-full h-[52px]"
            />
          </div>
          <div className="inner-group relative">
            <label
              className="inner-label block font-[600] text-[14px] text-[#606060] mb-[11px]"
              htmlFor="status"
            >
              Trạng thái
            </label>
            <select
              name="status"
              id="status"
              className="bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] font-[500] text-[14px] text-[var(--color-text)] w-full h-[52px]"
            >
              <option value="">
                Hoạt động
              </option>
              <option value="">
                Tạm dừng
              </option>
            </select>
          </div>
          <div className="inner-group relative inner-two-col md:col-span-2 col-span-1">
            <label
              className="inner-label block font-[600] text-[14px] text-[#606060] mb-[11px]"
              htmlFor="avatar"
            >Ảnh đại diện
            </label>
            <input
              type="file"
              name="avatar"
              id="avatar"
              className="bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] font-[500] text-[14px] text-[var(--color-text)] w-full h-[52px]"
              accept="image/*"
              filepond-image="filepond-image"
            />
          </div>
          <div className="inner-group relative inner-two-col md:col-span-2 col-span-1">
            <label
              className="inner-label block font-[600] text-[14px] text-[#606060] mb-[11px]"
              htmlFor="description">
              Mô tả
            </label>
            {/* <textarea
              name="description"
              id="description"
              className="bg-[#F5F6FA] border border-[#D5D5D5] rounded-[4px] px-[22px] font-[500] text-[14px] text-[var(--color-text)] w-full h-[160px] py-[18px] px-[22px]"
              textarea-mce
            >
            </textarea> */}
            {TinyMCEEditorComponent}
          </div>
          <div className="inner-button inner-two-col md:col-span-2 col-span-1 text-center">
            <button className="w-[274px] h-[56px] bg-[var(--color-primary)] rounded-[12px] font-[700] text-[18px] text-white opacity-[0.9] border-0 cursor-pointer hover:opacity-[1]">
              Tạo mới
            </button>
          </div>
        </form>
        <div className="inner-back text-center mt-[30px]">
          <Link
            href="/category"
            className="font-[700] text-[18px] text-[var(--color-primary)] underline"
          >
            Quay lại danh sách
          </Link>
        </div>
      </div>
    </>
  )
}
