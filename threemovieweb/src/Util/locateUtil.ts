export const locateDetail = (movieId : string) => {
	document.location.href = `/detail/movie?movie=${movieId}`
}

export const locateBookData = (movieId? : string, brchKR? : string) => {
	if(movieId && brchKR)
		document.location.href = `/book?movie=${movieId}&brch=${brchKR}`
	
	else if(movieId)
		document.location.href = `/book?movie=${movieId}`
	
	else if(brchKR)
		document.location.href = `/book?brch=${brchKR}`
	
	else
		document.location.href = `/book`
}

export const locateLogin = () => {
	document.location.href = '/user/login'
}

export const locateMyPage = () => {
	document.location.href = '/user/mypage'
}

export const locateMain = () => {
	document.location.href = '/main'
}

export const locateSignUp = () => {
	document.location.href = '/user/signup'
}

export const locateFindPass = () => {
	document.location.href = '/user/find/pass'
}

export const locateMovie = (movieId? : string) => {
	if(movieId)
		document.location.href = `/movie?movie=${movieId}`
		
	else
		document.location.href = `/movie`
}
