angular.module('utils.error', ['ui.router']);

angular.module('utils.error').factory(
    'errorHandlerInterceptor',
    function ($injector, $q) {
        return {
            'responseError': function (response) {
                return $q.reject(response);
            }
        };
    }
);
