package co.rapidor.domain.config

final case class AppConfig(proxy: ProxyConfig, backend: BackendConfig, tracer: TracerHost, zipkinTracer: TracerHost)
