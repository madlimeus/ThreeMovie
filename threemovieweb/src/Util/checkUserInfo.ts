export const checkEmail = (email: string) => {
    const email_format = /^([0-9a-zA-Z_.-]+)@([0-9a-zA-Z_-]+)(.[0-9a-zA-Z_-]+){1,2}$/;

    if (email_format.test(email)) return true;
    return false;
};

export const checkNickName = (nickName: string) => {
    return nickName.length >= 2 && nickName.length <= 10;
};

export const checkPassConfirm = (pass: string, passConfirm: string) => {
    return pass === passConfirm;
};

export const checkPass = (pass: string) => {
    if (pass.length >= 8 && pass.length <= 20) return true;
    return false;
};

export const checkAuthCode = (authCode: string) => {
    return authCode.length === 8;
};
