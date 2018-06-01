# 🤖 The Movie DB para ![alt text](https://io.fastshop.com.br/wcsstore/FastShop/Criacao/Atualizacoes/logoFastShopHome.png "FastShop") 

## Instalação
Para instalar o app você precisa acessar o link abaixo e seguir as instruções:

[Beta by Crashlytics](https://betas.to/gpUDo9Rk)

Ou faça o download do apk [aqui](https://github.com/henriquecocito/The-Movie-DB/blob/master/app-debug.apk).

## Desenvolvimento
Esse app foi desenvolvido utilizando Java. 

Uma das melhores formas de se organizar o projeto é utilizando a [Clean Architecture](https://medium.com/@dmilicic/a-detailed-guide-on-developing-android-apps-using-the-clean-architecture-pattern-d38d71e94029). São boas práticas que ajudam a construir um app que seja fácil de testar e de fazer manutenção.

![alt text](https://raw.githubusercontent.com/bufferapp/android-clean-architecture-boilerplate/master/art/architecture.png "Clean Architecture")


## Bibliotecas
Algumas bibliotecas foram usadas nesse app, mas só aquelas que fazem a diferença e, ao mesmo tempo, não me fazem reinventar a roda. Segue a lista:

* [Retrofit](http://square.github.io/retrofit/) - Requisições HTTP
* [Glide](https://github.com/bumptech/glide) - Download de imagens
* [Gson](https://github.com/google/gson) - Object mapper
* [Crashlytics](https://fabric.io/kits/android/crashlytics) - Distribuição do app e análise de crashes

## Testes
**Testes unitários** foram feitos utilizando [jUnit](https://junit.org/junit4/) e [Mockito](http://site.mockito.org/) e devem ser implementados nas camadas **Presentation** e **Domain**.

## Author
Herique Rodrigues Cocito - [LinkedIn](https://linkedin.com/in/henriquecocito)  
[henriquecocito@gmail.com](mailto:henriquecocito@gmail.com)  