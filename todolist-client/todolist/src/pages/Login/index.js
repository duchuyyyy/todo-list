import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Form, Input } from 'antd';
import { Link } from "react-router-dom";
import { authenticate } from '../../services/UserService';
import "./Login.scss";
import { setAccessToken, setIdUser, setRefreshToken } from '../../helpers/localStorageService';
import { useNavigate  } from "react-router-dom";

function Login() {
  const navigate = useNavigate();
  const onFinish = async (values) => {
    console.log('Received values of form: ', values);
    const data = {
      email: values.email,
      password: values.password,
    }
    
    const result = await authenticate(data);
    console.log(result);
    if(result !== undefined) {
      setAccessToken(result.headers.authorization);
      setRefreshToken(result.headers.refresh);
      const id = result.data.substring(2);
      setIdUser(id);
      navigate("/");
    }
    else {
      alert("Email is invalid or password is incorrect!");
    }
  };

  
  return (
    <Form
      name="normal_login"
      className="login-form"
      initialValues={{
        remember: true,
      }}
      onFinish={onFinish}
    >
      <Form.Item
        name="email"
        rules={[
          {
            required: true,
            message: 'Please input your Email!',
          },
        ]}
      >
        <Input prefix={<UserOutlined className="login-form-item-icon" />} placeholder="Email" />
      </Form.Item>
      <Form.Item
        name="password"
        rules={[
          {
            required: true,
            message: 'Please input your Password!',
          },
        ]}
      >
        <Input.Password
          prefix={<LockOutlined className="site-form-item-icon" />}
          type="password"
          placeholder="Password"
        />
      </Form.Item>
      <Form.Item>

        Forgot password
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit" className="login-form-button">
          Log in
        </Button>
        Or  <Link to="/register">register now!</Link>
      </Form.Item>
    </Form>
  );
}
export default Login;

