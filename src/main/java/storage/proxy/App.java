package storage.proxy;

import com.microsoft.azure.storage.blob.BlockBlobURL;
import com.microsoft.azure.storage.blob.ContainerURL;
import com.microsoft.azure.storage.blob.ListBlobsOptions;
import com.microsoft.azure.storage.blob.PipelineOptions;
import com.microsoft.azure.storage.blob.ServiceURL;
import com.microsoft.azure.storage.blob.SharedKeyCredentials;
import com.microsoft.azure.storage.blob.StorageURL;
import com.microsoft.azure.storage.blob.models.BlobItem;
import com.microsoft.azure.storage.blob.models.ContainerListBlobFlatSegmentResponse;
import com.microsoft.rest.v2.http.HttpClient;
import com.microsoft.rest.v2.http.HttpClientConfiguration;
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.util.FlowableUtil;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.util.Locale;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args) throws InvalidKeyException, MalformedURLException {
        // From the Azure portal, get your Storage account's name and account key.
        String accountName = "<storage-account-name>";
        String accountKey = "<storage-acoount-key>";

        SharedKeyCredentials credential = new SharedKeyCredentials(accountName, accountKey);
        //
        Proxy httpProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
        HttpClientConfiguration httpClientConfiguration = new HttpClientConfiguration(httpProxy);
        HttpClient httpClient = HttpClient.createDefault(httpClientConfiguration);
        //
        HttpPipeline pipeline = StorageURL.createPipeline(credential, new PipelineOptions().withClient(httpClient));

        URL u = new URL(String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName));

        ServiceURL serviceURL = new ServiceURL(u, pipeline);

        ContainerURL containerURL = serviceURL.createContainerURL("myjavacontainerbasic" + System.currentTimeMillis());

        BlockBlobURL blobURL = containerURL.createBlockBlobURL("HelloWorld.txt");

        String data = "Hello world!";

        containerURL.create(null, null, null)
                .flatMap(containerCreateResponse ->
                        blobURL.upload(Flowable.just(ByteBuffer.wrap(data.getBytes())), data.length(),
                                null, null, null, null))
                .flatMap(blobUploadResponse ->
                        blobURL.download(null, null, false, null))
                .flatMap(blobDownloadResponse ->
                        // Verify that the blob data round-tripped correctly.
                        FlowableUtil.collectBytesInBuffer(blobDownloadResponse.body(null))
                                .doOnSuccess(byteBuffer -> {
                                    if (byteBuffer.compareTo(ByteBuffer.wrap(data.getBytes())) != 0) {
                                        throw new Exception("The downloaded data does not match the uploaded data.");
                                    }
                                }))
                .flatMap(byteBuffer ->
                        containerURL.listBlobsFlatSegment(null, new ListBlobsOptions().withMaxResults(1), null))
                .flatMap(containerListBlobFlatSegmentResponse ->
                        listBlobsFlatHelper(containerURL, containerListBlobFlatSegmentResponse))
                .flatMap(containerListBlobFlatSegmentResponse ->
                        blobURL.delete(null, null, null))
                .flatMap(blobDeleteResponse ->
                        containerURL.delete(null, null))
                .blockingGet();
    }

    public static Single<ContainerListBlobFlatSegmentResponse> listBlobsFlatHelper(
            ContainerURL containerURL, ContainerListBlobFlatSegmentResponse response) {

        if (response.body().segment().blobItems() != null) {
            for (BlobItem b : response.body().segment().blobItems()) {
                String output = "Blob name: " + b.name();
                if (b.snapshot() != null) {
                    output += ", Snapshot: " + b.snapshot();
                }
                System.out.println(output);
            }
        }

        if (response.body().nextMarker() == null) {
            return Single.just(response);
        } else {
            String nextMarker = response.body().nextMarker();
            return containerURL.listBlobsFlatSegment(nextMarker, new ListBlobsOptions().withMaxResults(1), null)
                    .flatMap(containersListBlobFlatSegmentResponse ->
                            listBlobsFlatHelper(containerURL, containersListBlobFlatSegmentResponse));
        }
    }
}
