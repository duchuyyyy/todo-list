import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { resetPassword } from "../../services/UserService";
import "./ResetPassword.css"

function ResetPassword() {
  const { resetpasswordtoken } = useParams();
  const [passwordValue, setPasswordValue] = useState("");
  const navigate =  useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(resetpasswordtoken);
    const data = {
        password: passwordValue,
    }
    const result = await resetPassword(data, resetpasswordtoken);
    if(result.status === 200) {
        navigate("/reset-password/success");
    }
  };

  return (
    <div className="reset-password-container">
      <h2>Reset Password</h2>
      <form className="reset-password-form" onSubmit={handleSubmit}>
        <label htmlFor="password">New Password:</label>
        <input
          type="password"
          id="password"
          value={passwordValue}
          onChange={(e) => setPasswordValue(e.target.value)}
          required
          className="input--reset"
        />
        <button type="submit" className="button--submit">Reset Password</button>
      </form>
    </div>
  );
}

export default ResetPassword;
