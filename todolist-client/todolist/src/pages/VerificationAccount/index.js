import { useNavigate, useParams } from 'react-router';
import './VerificationAccount.css';
import { verifyAccount } from "../../services/UserService"

function VerificationAccount () {
  const params = useParams();
  const navigate = useNavigate();
  const handleSubmit = async (e) => {
    e.preventDefault();
    const confirmtoken = params.confirmtoken;
    try {
    const result = await verifyAccount(confirmtoken);
    if(result.status === 200) {
        navigate("/verification-account-success");
    }
    }
    catch (error) {
        alert(error);
    }
  };

  return (
    <div className="container">
      <h2 className="title">Account Verification</h2>
      <p className="description">Please click the button to verify account!</p>
      <form onSubmit={handleSubmit}>
        <button type="submit">Verify</button>
      </form>
    </div>
  );
}

export default VerificationAccount;