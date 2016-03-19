angular.module('utils', []);

angular.module('utils').factory('errorHandlerInterceptor',function ($injector, $q) {
        return {
            'responseError': function (response) {
                return $q.reject(response);
            }
        };
    }
);

angular.module('utils').factory('sessionHandlerInterceptor', function($injector, $$cookieReader) {
    return {
        request: function (config) {
            var AuthService = $injector.get('AuthService');

            if (!AuthService.getToken()) {
                AuthService.setToken($$cookieReader()['X-Token']);
            }

            config.headers['X-Token'] = AuthService.getToken();

            return config;
        }
    };
});