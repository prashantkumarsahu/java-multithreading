package multithreading.Future_CompletableFuture;//package multithreading.frameworks;
//
//public class CompletableFutureExample2 {
//
//    public static void main(String[] args) {
//
//
//        // repo call to search bookings with the given term
//        CompletableFuture<List<Booking>> bookingFuture = CompletableFuture.supplyAsync
//                (() -> bookingRepo.findIdAndNameByNameContains(searchTerm, PageRequest.of(0,5))
//                        .getContent());
//
//// repo call to search opportunities with the given term
//        CompletableFuture<List<Opportunity>> opportunityFuture= CompletableFuture.supplyAsync
//                (() -> opportunityRepo.findByNameContains(searchTerm, PageRequest.of(0,5))
//                        .getContent());
//
//// rest call to MIQ service, which in turn makes repo call, to fetch Advertisers
//        CompletableFuture<List<MiqAdvertiserDTO>> miqAdvertiserFuture =
//                CompletableFuture.supplyAsync(() -> {
//                    List<MiqAdvertiserDTO> miqAdvertiserDTOS = new ArrayList<>();
//                    try {
//                        miqAdvertiserDTOS =  miqRestService.getMiqAdvertiserByNameContaining(searchTerm,
//                                0,5);
//                    } catch (IOException e) {
//                        LOGGER.error("IO exception in fetching offices : {}", e.toString()); }
//                    return miqAdvertiserDTOS;
//                });
//
//
//        CompletableFuture<List<OfficeDTO>> officeFuture = CompletableFuture.supplyAsync(() -> {
//            List<OfficeDTO> officeDTOs = new ArrayList<>();
//            try {
//                officeDTOs =  miqRestService.getOfficesByName(searchTerm,0,5);
//            } catch (IOException e) {
//                LOGGER.error("IO exception in fetching offices : {}", e.toString()); }
//            return officeDTOs;
//        });
//
//        CompletableFuture<List<UserDTO>> userFuture = CompletableFuture.supplyAsync(() -> {
//            List<UserDTO> userDTOS = new ArrayList<>();
//            try {
//                userDTOS = miqRestService.getUsersByName(searchTerm,0,5);
//            } catch (IOException e) {
//                LOGGER.error("IO exception in fetching users : {}", e.toString()); }
//            return userDTOS;
//        });
//
//// wait for all futures to complete and Collate all the futures
//        CompletableFuture.allOf(bookingFuture,opportunityFuture, miqAdvertiserFuture, officeFuture,
//                userFuture);
//
//// Transform all different results into one ResultDTO format
//        List<ResultDTO> bookingResults = bookingFuture.get().stream().map
//                (booking -> new ResultDTO(booking.getBookingId(),booking.getName())).collect
//                (Collectors.toList());
//        List<ResultDTO> opportunityResults = opportunityFuture.get().stream().map
//                (opportunity -> new ResultDTO(opportunity.getId(),opportunity.getName())).collect
//                (Collectors.toList());
//        List<ResultDTO> miqAdvertiserResults = miqAdvertiserFuture.get().stream().map
//                (advertiser -> new ResultDTO(advertiser.getId(),advertiser.getName())).collect
//                (Collectors.toList());
//        List<ResultDTO> officeResults = officeFuture.get().stream().map
//                (office -> new ResultDTO(office.getId(),office.getName())).collect(Collectors.toList());
//
//        return these lists as search suggestion items;
//
//
//    }
//}
