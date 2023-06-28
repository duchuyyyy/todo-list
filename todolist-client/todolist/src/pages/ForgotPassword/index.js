import React, { useState } from 'react';
import { forgotPassword } from '../../services/UserService';
import './ForgotPassword.css';
import { useNavigate  } from "react-router-dom";

const ForgotPasswordPage = () => {
  const [emailValue, setEmailValue] = useState('');
  const navigate = useNavigate();

  const handleEmailChange = (e) => {
    setEmailValue(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const data = {
        email: emailValue
    }
    const result = await forgotPassword(data);
    console.log(result);
    navigate("/forgot-password/success")
  };

  return (
    <div className="forgot-password-container">
      <h1 className="forgot-password-title">Forgot Password</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="email" className="forgot-password-label">
          Email:
        </label>
        <input
          type="email"
          id="email"
          value={emailValue}
          onChange={handleEmailChange}
          className="forgot-password-input"
          required
        />
        <button type="submit" className="forgot-password-button">Submit</button>
      </form>
    </div>
  );
};

export default ForgotPasswordPage;

