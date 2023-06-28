import { NavLink } from "react-router-dom";
import "./ResetPasswordSuccess.css";

function ResetPasswordSuccess ()  {

    return (
      <div className="reset-password-success-container">
        <h2>Reset Password Successful</h2>
        <p>Your password has been successfully reset.</p>
        <p>You can now <NavLink to="/login">log in</NavLink> with your new password.</p>
      </div>
    );
  };
  
  export default ResetPasswordSuccess;