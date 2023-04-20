export const checkEmail = (email: string) => {
    const email_format = /^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]{2,6}$/;

    return email_format.test(email);
};

export const checkNickName = (nickName: string) => {
    return nickName && nickName.length >= 2 && nickName.length <= 10;
};

export const checkPassConfirm = (pass: string, passConfirm: string) => {
    return pass === passConfirm;
};

export const checkPass = (pass: string) => {
    const password_format = /^[A-Za-z0-9\d$@$!%*#?&]{8,20}$/;
    return password_format.test(pass);
};

export const checkAuthCode = (authCode: string) => {
    return authCode.length === 8;
};
