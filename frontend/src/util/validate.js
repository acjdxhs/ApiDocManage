//验证账户
var validateUsername=(rule, value, callback) => {
  // var reg=/^[a-zA-Z][a-zA-Z0-9]{7,15}$/;
  if (value === '') {
    callback(new Error('请输入帐号'));
  }else if(value!=""&&(value.length<4))
  {
    callback(new Error('密码长度需大于等于4位'));
  }
  // }else if(!reg.test(value)){
  //   callback(new Error('请输入以字母开头、由8-16位字母或数字组成的帐号'));
  // }
  else {
    callback();
  }

};

//密码校验
var validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'));
  } else if(value!=""&&(value.length<4)){
    callback(new Error('密码长度需大于等于4位'));
  }else {
    callback();
  }
};

//登录
export const loginRule={
  account:[{required:true, validator:validateUsername}],
  password:[{required: true,validator:validatePass}]
};

//注册r
export const signUpRule={
  account:[{required:true, validator:validateUsername}],
  password:[{required: true,validator:validatePass}],
  confirm: [{required: true,validator:validatePass}]
};
