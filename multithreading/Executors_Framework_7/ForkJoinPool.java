package multithreading.Executors_Framework_7;

import java.util.logging.Logger;

public class ForkJoinPool {

    Logger log;
    public static void main(String[] args) {

        // create threadPool
//        ForkJoinPool customThreadPool =
//                new ForkJoinPool(Math.min((dbmAdvertisers.size() != 0 ? dbmAdvertisers.size() : 1), 15));
//
//// submit all advertisers into pool
//        try {
//            customThreadPool.submit(() -> dbmAdvertisers.parallelStream().forEach(advertiser -> {
//                final String advertiserId = advertiser.getId();
//                try {
//                    final List<Creative> creatives =
//                            partnerIdsForEntityJobs.contains(advertiser.getPartnerId()) ?
//                                    creativeEntityService.getEntityList(displayVideo, advertiserId, orderByParam,
//                                            filters) :
//                                    Collections.emptyList();
//                    if (!creatives.isEmpty())
//                        saveDbmCreatives(creatives, advertiser.getSeat());
//                    else
//                        creativesEmptyAdvertisers.add(advertiserId);
//                } catch (Exception e) {
//                    failedAdvertisers.add(advertiserId);
//                }
//            })).join();
//
//        } catch (Exception e) {
//            log.error("Exception while fetching creatives {}", e.getStackTrace());
//            throw new Exception("Exception while fetching Creatives");
//        } finally {
//            customThreadPool.shutdown();
//        }

    }
}
