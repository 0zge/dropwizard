package com.yammer.dropwizard.jetty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.yammer.dropwizard.util.Duration;
import com.yammer.dropwizard.util.Size;
import com.yammer.dropwizard.util.SizeUnit;
import com.yammer.dropwizard.validation.MinDuration;
import com.yammer.dropwizard.validation.MinSize;
import com.yammer.dropwizard.validation.PortRange;
import com.yammer.dropwizard.validation.ValidationMethod;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

// TODO: 4/16/13 <coda> -- split out connectors to a polymorphic map (how to keep dw.http.port &etc overrides)?
// TODO: 4/16/13 <coda> -- write docs
// TODO: 4/16/13 <coda> -- write tests

/**
 * Configuration for a Jetty server.
 */
@SuppressWarnings("UnusedDeclaration")
public class ServerConfiguration {
    @Valid
    @NotNull
    @JsonProperty
    private RequestLogConfiguration requestLog = new RequestLogConfiguration();

    @Valid
    @NotNull
    @JsonProperty
    private GzipConfiguration gzip = new GzipConfiguration();

    @PortRange
    @JsonProperty
    private int port = 8080;

    @PortRange
    @JsonProperty
    private int adminPort = 8081;

    @Min(2)
    @JsonProperty
    private int maxThreads = 1024;

    @Min(1)
    @JsonProperty
    private int minThreads = 8;

    @Min(1)
    @JsonProperty
    private int acceptorThreads = Math.max(1, Runtime.getRuntime().availableProcessors() / 2);

    @Min(1)
    @JsonProperty
    private int selectorThreads = Runtime.getRuntime().availableProcessors();

    @Min(1)
    @JsonProperty
    private int acceptQueueSize = -1;

    @JsonProperty
    private boolean reuseAddress = true;

    @JsonProperty
    private Duration soLingerTime = null;

    @JsonProperty
    private boolean useServerHeader = false;

    @JsonProperty
    private boolean useDateHeader = true;

    @JsonProperty
    private boolean useForwardedHeaders = true;

    @JsonProperty
    private boolean useDirectBuffers = true;

    @JsonProperty
    private String bindHost = null;

    @JsonProperty
    private String adminUsername = null;

    @JsonProperty
    private String adminPassword = null;

    @NotNull
    @JsonProperty
    @MinSize(128)
    private Size headerCacheSize = Size.bytes(512);

    @NotNull
    @JsonProperty
    @MinSize(value = 8, unit = SizeUnit.KILOBYTES)
    private Size outputBufferSize = Size.kilobytes(32);

    @NotNull
    @JsonProperty
    @MinSize(value = 1, unit = SizeUnit.KILOBYTES)
    private Size maxRequestHeaderSize = Size.kilobytes(8);

    @NotNull
    @JsonProperty
    @MinSize(value = 1, unit = SizeUnit.KILOBYTES)
    private Size maxResponseHeaderSize = Size.kilobytes(8);

    @NotNull
    @JsonProperty
    @MinSize(value = 1, unit = SizeUnit.KILOBYTES)
    private Size inputBufferSize = Size.kilobytes(8);

    @NotNull
    @JsonProperty
    @MinDuration(value = 1, unit = TimeUnit.MILLISECONDS)
    private Duration idleTimeout = Duration.seconds(30);

    @NotNull
    @JsonProperty
    @MinSize(value = 1, unit = SizeUnit.KILOBYTES)
    private Size minBufferPoolSize = Size.bytes(64);

    @NotNull
    @JsonProperty
    @MinSize(value = 1, unit = SizeUnit.KILOBYTES)
    private Size bufferPoolIncrement = Size.bytes(1024);

    @NotNull
    @JsonProperty
    @MinSize(value = 1, unit = SizeUnit.KILOBYTES)
    private Size maxBufferPoolSize = Size.kilobytes(64);

    @NotNull
    @JsonProperty
    private Optional<Integer> maxQueuedRequests = Optional.absent();

    @ValidationMethod(message = "must have a smaller minThreads than maxThreads")
    public boolean isThreadPoolSizedCorrectly() {
        return minThreads <= maxThreads;
    }

    @ValidationMethod(message = "must have adminUsername if adminPassword is defined")
    public boolean isAdminUsernameDefined() {
        return (adminPassword == null) || (adminUsername != null);
    }

    @JsonIgnore
    public RequestLogConfiguration getRequestLogConfiguration() {
        return requestLog;
    }

    public void setRequestLogConfiguration(RequestLogConfiguration config) {
        this.requestLog = config;
    }

    @JsonIgnore
    public GzipConfiguration getGzipConfiguration() {
        return gzip;
    }

    public void setGzipConfiguration(GzipConfiguration config) {
        this.gzip = config;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getAdminPort() {
        return adminPort;
    }

    public void setAdminPort(int port) {
        this.adminPort = port;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int count) {
        this.maxThreads = count;
    }

    public int getMinThreads() {
        return minThreads;
    }

    public void setMinThreads(int count) {
        this.minThreads = count;
    }

    public int getAcceptorThreads() {
        return acceptorThreads;
    }

    public void setAcceptorThreads(int count) {
        this.acceptorThreads = count;
    }

    public int getAcceptQueueSize() {
        return acceptQueueSize;
    }

    public void setAcceptQueueSize(int size) {
        this.acceptQueueSize = size;
    }

    public boolean isReuseAddressEnabled() {
        return reuseAddress;
    }

    public void setReuseAddress(boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
    }

    public Optional<Duration> getSoLingerTime() {
        return Optional.fromNullable(soLingerTime);
    }

    public void setSoLingerTime(Duration duration) {
        this.soLingerTime = duration;
    }

    public boolean useForwardedHeaders() {
        return useForwardedHeaders;
    }

    public void setUseForwardedHeaders(boolean useForwardedHeaders) {
        this.useForwardedHeaders = useForwardedHeaders;
    }

    public boolean useDirectBuffers() {
        return useDirectBuffers;
    }

    public void setUseDirectBuffers(boolean useDirectBuffers) {
        this.useDirectBuffers = useDirectBuffers;
    }

    public Optional<String> getBindHost() {
        return Optional.fromNullable(bindHost);
    }

    public void setBindHost(String host) {
        this.bindHost = host;
    }

    public boolean isDateHeaderEnabled() {
        return useDateHeader;
    }

    public void setUseDateHeader(boolean useDateHeader) {
        this.useDateHeader = useDateHeader;
    }

    public boolean isServerHeaderEnabled() {
        return useServerHeader;
    }

    public void setUseServerHeader(boolean useServerHeader) {
        this.useServerHeader = useServerHeader;
    }

    public Optional<String> getAdminUsername() {
        return Optional.fromNullable(adminUsername);
    }

    public void setAdminUsername(String username) {
        this.adminUsername = username;
    }

    public Optional<String> getAdminPassword() {
        return Optional.fromNullable(adminPassword);
    }

    public void setAdminPassword(String password) {
        this.adminPassword = password;
    }

    public Size getHeaderCacheSize() {
        return headerCacheSize;
    }

    public void setHeaderCacheSize(Size headerCacheSize) {
        this.headerCacheSize = headerCacheSize;
    }

    public Size getOutputBufferSize() {
        return outputBufferSize;
    }

    public void setOutputBufferSize(Size outputBufferSize) {
        this.outputBufferSize = outputBufferSize;
    }

    public Size getMaxRequestHeaderSize() {
        return maxRequestHeaderSize;
    }

    public void setMaxRequestHeaderSize(Size maxRequestHeaderSize) {
        this.maxRequestHeaderSize = maxRequestHeaderSize;
    }

    public Size getMaxResponseHeaderSize() {
        return maxResponseHeaderSize;
    }

    public void setMaxResponseHeaderSize(Size maxResponseHeaderSize) {
        this.maxResponseHeaderSize = maxResponseHeaderSize;
    }

    public Size getInputBufferSize() {
        return inputBufferSize;
    }

    public void setInputBufferSize(Size inputBufferSize) {
        this.inputBufferSize = inputBufferSize;
    }

    public int getSelectorThreads() {
        return selectorThreads;
    }

    public void setSelectorThreads(int selectorThreads) {
        this.selectorThreads = selectorThreads;
    }

    public Duration getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Duration idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Size getMinBufferPoolSize() {
        return minBufferPoolSize;
    }

    public void setMinBufferPoolSize(Size minBufferPoolSize) {
        this.minBufferPoolSize = minBufferPoolSize;
    }

    public Size getBufferPoolIncrement() {
        return bufferPoolIncrement;
    }

    public void setBufferPoolIncrement(Size bufferPoolIncrement) {
        this.bufferPoolIncrement = bufferPoolIncrement;
    }

    public Size getMaxBufferPoolSize() {
        return maxBufferPoolSize;
    }

    public void setMaxBufferPoolSize(Size maxBufferPoolSize) {
        this.maxBufferPoolSize = maxBufferPoolSize;
    }

    public Optional<Integer> getMaxQueuedRequests() {
        return maxQueuedRequests;
    }

    public void setMaxQueuedRequests(Optional<Integer> maxQueuedRequests) {
        this.maxQueuedRequests = maxQueuedRequests;
    }
}
