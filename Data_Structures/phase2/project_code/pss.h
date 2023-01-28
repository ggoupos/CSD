/***************************************************************
 *
 * file: pss.h
 *
 * @Authors  Nikolaos Vasilikopoulos (nvasilik@csd.uoc.gr), John Petropoulos
 * @brief   Header file for the Public Subscribe System,
 * with the structures and function prototypes
 *
 ***************************************************************
 */

#ifndef pss_h
#define pss_h
#define MG 64

struct Info {
  int iId;
  int itm;
  int igp[MG];
  struct Info *ilc;
  struct Info *irc;
  struct Info *ip;
};
struct Subscription {
  int sId;
  struct Subscription *snext;
};
struct Group {
  int gId;
  struct Subscription *gsub;
  struct Info *gr;
};
struct SubInfo {
  int sId;
  int stm;
  struct TreeInfo *tgp[MG];
  struct TreeInfo *sgp[MG];
  struct SubInfo *snext;
};
struct TreeInfo {
  int tId;
  int ttm;
  struct TreeInfo *tlc;
  struct TreeInfo *trc;
  struct TreeInfo *tp;
  struct TreeInfo *right;
  struct TreeInfo *left;
};

/**
 * @brief Optional function to initialize data structures that
 *        need initialization
 *
 * @param m Size of hash table
 * @param p Prime number for the universal hash function
 *
 * @return 0 on success
 *         1 on failure
 */
int initialize(int m, int p);

/**
 * @brief Free resources
 *
 * @return 0 on success
 *         1 on failure
 */
int free_all(void);

/**
 * @brief Insert info
 *
 * @param iTM Timestamp of arrival
 * @param iId Identifier of information
 * @param gids_arr Pointer to array containing the gids of the Event.
 * @param size_of_gids_arr Size of gids_arr including -1
 * @return 0 on success
 *          1 on failure
 */
int Insert_Info(int iTM, int iId, int *gids_arr, int size_of_gids_arr);

/**
 * @brief Subsriber Registration
 *
 * @param sTM Timestamp of arrival
 * @param sId Identifier of subscriber
 * @param gids_arr Pointer to array containing the gids of the Event.
 * @param size_of_gids_arr Size of gids_arr including -1
 * @return 0 on success
 *          1 on failure
 */
int Subscriber_Registration(struct SubInfo **hashtable, int sTM, int sId,
                            int *gids_arr, int size_of_gids_arr, int m, int p);

/**
 * @brief Prune Information from server and forward it to client
 *
 * @param tm Information timestamp of arrival
 * @return 0 on success
 *          1 on failure
 */
int Prune(struct SubInfo **hashtable, int m, int p, int tm);

/**
 * @brief Consume Information for subscriber
 *
 * @param sId Subscriber identifier
 * @return 0 on success
 *          1 on failure
 */
int Consume(int sId, struct SubInfo **hashtable, int m, int p);

/**
 * @brief Delete subscriber
 *
 * @param sId Subscriber identifier
 * @return 0 on success
 *          1 on failure
 */
int Delete_Subscriber(struct SubInfo **hashtable, int sId, int m, int p);

/**
 * @brief Print Data Structures of the system
 *
 * @return 0 on success
 *          1 on failure
 */
int Print_all(struct SubInfo **hashtable, int m);

#endif /* pss_h */
