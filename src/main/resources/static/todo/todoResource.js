angular.module('todo').factory('ToDoResource', ['$resource', function ($resource) {
    return $resource('api/todo/:id', {id: '@id'}, {
        update: {
            method: 'PUT'
        }
    });
}]);
