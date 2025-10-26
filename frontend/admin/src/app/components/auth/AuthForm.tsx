export const AuthForm = (props: {
  label: string;
  id: string;
  type: string;
  placeholder?: string;
}) => {
  const { label, id, type, placeholder } = props;

  return (
    <>
      <div className="mb-[24px] relative">
        <label
          className="block mb-[12px] font-[600] text-[14px] text-[var(--color-text)] opacity-[0.8]"
          htmlFor={id}
        >
          {label}
        </label>
        <input
          className="w-full h-[45px] bg-[#F1F4F9] border border-[#D8D8D8] rounded-[8px] py-0 px-[13px] font-[600] text-[14px] text-[var(--color-text)] placeholder:text-[#A6A6A6]"
          type={type}
          name={id}
          id={id}
          placeholder={placeholder}
        />
      </div>
    </>
  )
}
