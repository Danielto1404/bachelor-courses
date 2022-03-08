# nlp.ai

This is web application which helps you to
learn **[NLP (Natural Language Processing)](https://en.wikipedia.org/wiki/Natural_language_processing)** techniques.

You can run NLP models, download pretrained and read papers about them.

### Launching server:

```bash
python main
```

### Run tests:

**Unit:**

  ```shell
chmod u+x run_unit_tests.sh
 ./run_unit_tests.sh
  ```

**Integration:**

  ```shell
chmod u+x run_integration_tests.sh
 ./run_integration_tests.sh
  ```

### Extra configurations:

Also, you can configure **[config.yml](config.yml)** file for custom server settings

+ ### Used frameworks:
    + [FastAPI](https://fastapi.tiangolo.com/) - Web app framework
    + [Uvicorn](https://www.uvicorn.org/) - Python ASGI web app server
    + [Gunicorn](https://gunicorn.org/) - Python WSGI web app server

#### [API documentation](http://localhost:8000/docs)
