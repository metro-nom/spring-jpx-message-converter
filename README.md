# Spring message converter for the JPX library

Utility class for integrating the [Jenetic's JPX library](https://github.com/jenetics/jpx) with the serialization logic
of Spring. Using this converter, Spring automatically uses JPX's own serialization to read and write GPX objects, 
ensuring a valid GPX document is produced.

## Usage

The class `GpxMessageConverter` implements the `HttpMessageConverter` interface from Spring-web, and can be used where 
ever Spring allows injecting its implementation. A detailed description can be found at
https://www.baeldung.com/spring-httpmessageconverter-rest.

## Installation

The project is still in its setup. Unfortunately there is no release yet.

