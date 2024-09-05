//package multithreading.Future_CompletableFuture;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class CompletableFutureExample {
//
//    public static void main(String[] args) {
//
//        // create threadpool
//        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_PARALLEL_REQUESTS);
//
//// for each seat
//        partnerIdsMap.keySet().forEach(seat -> {
//
//// get all SDF Tasks for that seat
//            List<CreateSdfDownloadTaskRequest> sdfDownloadTaskRequests =
//                    youtubeSdfService.getSdfCreateRequestsForAllAdvertisers(this.partnerIdsMap.get(seat),
//                            List.of(entities.split(Constants.COMMA)));
//
//// run those SDF Tasks in parallel
//            List<CompletableFuture<Void>> sdfResponses =
//                    sdfDownloadTaskRequests.stream().map(sdfRequestBatch -> CompletableFuture.runAsync(() -> {
//
//                        try {
//                            String response = youtubeSdfService.sendSdfCreateRequest(sdfRequestBatch, service);
//                            String downloadPath = youtubeSdfService.checkRequestAndGetDownloadPath(response, service);
//                            String extractFileDirectory = youtubeSdfService.downloadAndSaveEntities(downloadPath, service);
//
//                            validateAndPersistEntities(extractFileDirectory, seat);
//
//                        } catch (Exception e) {
//                            log.error("failed to download batch of SDFs for seat {} with reason {}", seat, e.toString());
//                        }
//                    }, executor)).collect(Collectors.toList());
//
//// wait for all threads/tasks to complete before moving to next steps;
//            sdfResponses.forEach(CompletableFuture::join);
//
//        });
//
//    });
//
//executor.shutdown();
//
//if(!executor.awaitTermination(1,TimeUnit.HOURS))
//
//    {
//        executor.shutdownNow();
//    }
//
//}
//}
