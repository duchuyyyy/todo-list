import { Button, Form, Input } from 'antd';
import { useNavigate } from 'react-router';
import { register } from '../../services/UserService';
import "./Register.scss"



function Register() {
  const navigate  = useNavigate();

  const onFinish = async (values) => {
    console.log('Success:', values);
    if(values.password === values.confirmpassword) {
      const data = {
        email: values.email,
        password: values.password,
      }
      const result = await register(data);
      console.log(result);
      navigate("/register/success");
    }
  };

  const onFinishFailed = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };

  const validateEmail = (_, value) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!value || !emailRegex.test(value)) {
      return Promise.reject('Please enter a valid email address');
    }
    return Promise.resolve();
  };

  return  (
  <Form
    className="register-form"
    name="basic"
    labelCol={{
      span: 8,
    }}
    wrapperCol={{
      span: 16,
    }}
    style={{
      maxWidth: 600,
    }}
    initialValues={{
      remember: true,
    }}
    onFinish={onFinish}
    onFinishFailed={onFinishFailed}
    autoComplete="off"
  >
    <Form.Item
      label="Email"
      name="email"
      rules={[
        {
          required: true,
          message: 'Please input your email!',
          validator: validateEmail
        },
      ]}
    >
      <Input type="email"/>
    </Form.Item>

    <Form.Item
      label="Password"
      name="password"
      rules={[
        {
          required: true,
          message: 'Please input your password!',
        },
      ]}
    >
      <Input.Password />
    </Form.Item>

    <Form.Item
      label="Confirm password"
      name="confirmpassword"
      rules={[
        {
          required: true,
          message: 'Please input your confirm password!',
        },
      ]}
    >
      <Input.Password />
    </Form.Item>

    <Form.Item
      wrapperCol={{
        offset: 8,
        span: 16,
      }}
    >
      <Button type="primary" htmlType="submit">
        Register
      </Button>
    </Form.Item>
  </Form>
    )
}

export default Register;