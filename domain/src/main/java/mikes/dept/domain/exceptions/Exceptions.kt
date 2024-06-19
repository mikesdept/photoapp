package mikes.dept.domain.exceptions

import java.io.IOException

class NoInternetConnectionException : IOException("No Internet Connection Exception")

class ConnectionTimeoutException : IOException("Connection Time Out Exception")
