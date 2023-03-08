import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import "bootstrap/dist/css/bootstrap.css"
import axios from "axios";

const refreshToken = localStorage.getItem('refreshToken');
const accessToken = localStorage.getItem('accessToken');

axios.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('accessToken');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Axios Interceptor를 사용하여 응답에 대한 에러를 처리합니다.
axios.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;
        if (
            error.response.status === 401 &&
            originalRequest.url === '/api/auth/reissue'
        ) {
            localStorage.removeItem('refreshToken');
            localStorage.removeItem('accessToken');
            localStorage.removeItem('accessTokenExpiresIn');
            alert("토큰이 만료되었습니다. 다시 로그인해주세요.");
            return Promise.reject(error);
        }

        if (error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;
            try {
                const res = await axios.post('/api/auth/reissue', {
                    accessToken: accessToken,
                    refreshToken: refreshToken,
                });
                const reAccessToken = res.data.accessToken;
                const reRefreshToken = res.data.refreshToken
                const reAccessTokenExpiresIn = res.data.accessTokenExpiresIn
                localStorage.setItem('accessToken', reAccessToken);
                localStorage.setItem('refreshToken', reRefreshToken);
                localStorage.setItem('accessTokenExpiresIn', reAccessTokenExpiresIn);

                return axios.request(originalRequest);
            } catch (err) {
                localStorage.removeItem('refreshToken');
                localStorage.removeItem('accessToken');
                localStorage.removeItem('accessTokenExpiresIn');
                alert("토큰이 만료되었습니다. 다시 로그인해주세요.");
                return Promise.reject(error);
            }
        }
        return Promise.reject(error);
    }
);

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
    <App />
);