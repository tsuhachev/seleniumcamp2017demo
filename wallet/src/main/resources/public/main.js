angular.module('demo', [])
.controller('Hello', function($scope, $http) {
    $http.get(location.origin + '/currencies').
                then(function(response) {
                    $scope.currencies = response.data;
                });

    $scope.getBalance = function(selectedCurrency) {
       $http.get(location.origin + '/balance?targetCurrencyCode=' + selectedCurrency)
             .then(function(response) {
                   $scope.balance = response.data;
               });
    }
})
.controller('ChangeCurrency', function($scope, $http) {


});