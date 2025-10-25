"use client"
import JustValidate from "just-validate";
import Link from "next/link";
import { useEffect, useState } from "react";
import { AuthForm } from "@/app/components/auth/AuthForm";

export default function RegisterPage() {

  const emailFields = [
    {
      label: "Họ tên",
      id: "fullName",
      type: "text",
      placeholder: "Ví dụ: Lê Văn A"
    },
    {
      label: "Email",
      id: "email",
      type: "email",
      placeholder: "Ví dụ: levana@gmail.com"
    },
    {
      label: "Mật khẩu",
      id: "password",
      type: "password",
      placeholder: ""
    }
  ];

  const phoneFields = [
    {
      label: "Họ tên",
      id: "fullName",
      type: "text",
      placeholder: "Ví dụ: Lê Văn A"
    },
    {
      label: "Số điện thoại",
      id: "phone",
      type: "tel",
      placeholder: "Ví dụ: 0912345678"
    },
    {
      label: "Mật khẩu",
      id: "password",
      type: "password",
      placeholder: ""
    }
  ];

  const [loginType, setLoginType] = useState(true);
  const formFields = loginType ? phoneFields : emailFields;

  useEffect(() => {

    const validator = new JustValidate("#loginForm", {
      errorLabelStyle: {
        position: 'absolute',
        top: '104%',
        left: '0',
        width: '100%',
        fontSize: '12px',
        color: '#e63946'
      }
    });

    // Xóa lỗi cũ (nếu còn sót trong DOM)
    document.querySelectorAll(".just-validate-error-label").forEach(el => el.remove());

    if (loginType) {
      validator
        .addField("#phone", [
          {
            rule: "required",
            errorMessage: "Vui lòng nhập số điện thoại!"
          },
          {
            rule: "customRegexp",
            value: /^(0|\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-6|8|9]|9[0-4|6-9])[0-9]{7}$/,
            errorMessage: "Số điện thoại không đúng định dạng!"
          }
        ])
    } else {
      validator
        .addField('#email', [
          {
            rule: 'required',
            errorMessage: 'Vui lòng nhập email!',
          },
          {
            rule: 'email',
            errorMessage: 'Email không đúng định dạng!',
          },
        ])
    }
    validator
      .addField("#fullName", [
        {
          rule: "required",
          errorMessage: "Vui lòng nhập họ tên!"
        },
        {
          rule: "minLength",
          value: 5,
          errorMessage: "Họ tên phải có ít nhất 5 ký tự!"
        },
        {
          rule: "maxLength",
          value: 50,
          errorMessage: "Họ tên không được vượt quá 50 ký tự!"
        }
      ])
      .addField('#password', [
        {
          rule: "required",
          errorMessage: "Vui lòng nhập mật khẩu của bạn!"
        },
        {
          rule: "minLength",
          value: 8,
          errorMessage: "Mật khẩu phải chứa ít nhất 8 ký tự!"
        },
        {
          validator: (value: string) => {
            const regex = /[A-Z]/;
            const result = regex.test(value);
            return result;
          },
          errorMessage: "Mật khẩu phải chứa ký tự viết hoa!"
        },
        {
          validator: (value: string) => {
            const regex = /[a-z]/;
            const result = regex.test(value);
            return result;
          },
          errorMessage: "Mật khẩu phải chứa ký tự viết thường!"
        },
        {
          validator: (value: string) => {
            const regex = /[0-9]/;
            const result = regex.test(value);
            return result;
          },
          errorMessage: "Mật khẩu phải chứa chữ số!"
        },
        {
          validator: (value: string) => {
            const regex = /[^a-zA-Z0-9]/;
            const result = regex.test(value);
            return result;
          },
          errorMessage: "Mật khẩu phải chứa ký tự đặc biệt!"
        },
      ])
      .onSuccess(() => {
        // Call API

      });

    return () => {
      validator.destroy();
      document.querySelectorAll(".just-validate-error-label").forEach(el => el.remove());
    };

  }, [loginType]);

  return (
    <>
      <div className="bg-[url('/assets/images/bg-account.svg')] py-[50px] min-h-[100vh] bg-cover bg-no-repeat bg-center">
        <div className="bg-[#fff] rounded-[24px] md:w-[630px] w-[95%] mx-auto md:py-[90px] py-[40px] md:px-[57px] px-[20px]">
          <h2 className="font-[700] text-[32px] text-[var(--color-text)] text-center mt-[0] mb-[15px]">
            Đăng ký
          </h2>
          <p className="font-600 text-[18px] text-[var(--color-text)] opacity-[0.8] text-center mt-0 mb-[40px]">
            Tạo một tài khoản để tiếp tục
          </p>
          <form
            className="mb-[30px]"
            id="loginForm"
          >
            {formFields && (
              formFields.map((item, index) => (
                <AuthForm
                  key={index}
                  label={item.label}
                  id={item.id}
                  type={item.type}
                  placeholder={item.placeholder}
                />
              ))
            )}
            <div className="mb-[30px] flex justify-between">
              <div className="flex items-center gap-[12px] relative">
                <input
                  className="w-[24px] h-[24px]"
                  type="checkbox"
                  name="rememberPassword"
                  id="remember-password"
                />
                <label
                  className="font-[600] text-[18px] text-[var(--color-text)] opacity-60"
                  htmlFor="remember-password"
                >
                  Tôi chấp nhận các điều khoản và điều kiện
                </label>
              </div>
            </div>
            <button className="w-full h-[56px] border-0 rounded-[8px] bg-[var(--color-primary)] opacity-90 font-[700] text-[20px] text-white cursor-pointer hover:opacity-100">
              Đăng Ký
            </button>
          </form>
          <div className="text-center">
            <Link
              className="font-[700] text-[18px] text-[#5A8CFF] underline block mb-[10px]"
              href="#"
              onClick={() => setLoginType(!loginType)}
            >
              {!loginType
                ? "Đăng ký bằng số điện thoại"
                : "Đăng ký bằng email"
              }
            </Link>
          </div>
          <div className="text-center">
            <span className="font-[600] text-[18px] text-[var(--color-text)] opacity-65">
              Bạn đã có tài khoản?
            </span>
            <Link
              className="font-[700] text-[18px] text-[#5A8CFF] underline ml-[10px]"
              href="/login"
            >
              Đăng nhập
            </Link>
          </div>
        </div>
      </div>
    </>
  )
}
