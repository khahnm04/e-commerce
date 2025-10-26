/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable @typescript-eslint/no-explicit-any */
"use client"
import Link from "next/link";
import { useEffect, useState } from "react";
import JustValidate from 'just-validate';
import { useRouter } from "next/navigation";
import { AuthForm } from "@/app/components/auth/AuthForm";

export default function LoginPage() {

  const emailFields = [
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
  const router = useRouter();

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
      .onSuccess((event: any) => {
        // Call API
        const identifier = loginType ? event.target.phone.value : event.target.email.value;
        const password = event.target.password.value;

        const dataFinal = loginType
          ? { phoneNumber: identifier, password: password }
          : { email: identifier, password: password };

        fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/login`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(dataFinal),
          credentials: "include"
        })
          .then(res => res.json())
          .then(data => {
            console.log(data);
            if (!data.success) {
              alert("Đăng nhập thất bại");
            }
            if (data.success) {
              router.push("/");
            }
          })
      });
    return () => {
      validator.destroy();
      document.querySelectorAll(".just-validate-error-label").forEach(el => el.remove());
    };
  }, [loginType]);

  return (
    <>
      <div className="bg-[url('/assets/images/bg-account.svg')] py-[40px] min-h-[100vh] bg-cover bg-no-repeat bg-center">
        <div className="bg-[#fff] rounded-[19.2px] md:w-[504px] w-[95%] mx-auto md:py-[72px] py-[32px] md:px-[45.6px] px-[16px]">
          <h2 className="font-[700] text-[25.6px] text-[var(--color-text)] text-center mt-[0] mb-[12px]">
            Đăng nhập
          </h2>
          <p className="font-[600] text-[14.4px] text-[var(--color-text)] opacity-[0.8] text-center mt-0 mb-[32px]">
            {loginType
              ? "Vui lòng nhập số điện thoại và mật khẩu để tiếp tục"
              : "Vui lòng nhập email và mật khẩu để tiếp tục"
            }
          </p>
          <form
            id="loginForm"
            className="mb-[24px]"
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
            <div className="mb-[24px] flex justify-between">
              <div className="flex items-center gap-[9.6px] relative">
                <input
                  className="w-[19.2px] h-[19.2px]"
                  type="checkbox"
                  name="rememberPassword"
                  id="remember-password"
                />
                <label
                  className="font-[600] text-[14.4px] text-[var(--color-text)] opacity-60"
                  htmlFor="remember-password">
                  Nhớ mật khẩu
                </label>
              </div>
              <Link
                className="font-[600] text-[14.4px] text-[var(--color-text)] opacity-60 hover:text-[var(--color-primary)] hover:opacity-100"
                href="#"
              >
                Quên mật khẩu?
              </Link>
            </div>
            <button className="w-full h-[44.8px] border-0 rounded-[8px] bg-[var(--color-primary)] opacity-90 font-[700] text-[16px] text-white cursor-pointer hover:opacity-100">
              Đăng Nhập
            </button>
          </form>
          <div className="text-center">
            <Link
              className="font-[700] text-[14.4px] text-[#5A8CFF] underline block mb-[8px]"
              href="#"
              onClick={() => setLoginType(!loginType)}
            >
              {!loginType
                ? "Đăng nhập bằng số điện thoại"
                : "Đăng nhập bằng email"
              }
            </Link>
          </div>
          {/* <div className="text-center">
            <span className="font-[600] text-[14.4px] text-[var(--color-text)] opacity-65">
              Bạn chưa có tài khoản?
            </span>
            <Link
              className="font-[700] text-[14.4px] text-[#5A8CFF] underline ml-[8px]"
              href="/register"
            >
              Tạo tài khoản
            </Link>
          </div> */}
        </div>
      </div>
    </>
  )
}
